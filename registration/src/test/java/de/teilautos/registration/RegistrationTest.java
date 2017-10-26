/*
 Copyright (c) 2017 Videa Project Services GmbH

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software,and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package de.teilautos.registration;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;

import java.io.IOException;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import de.teilautos.encryption.AesEncrypter;
import de.teilautos.io.FileReader;
import de.teilautos.io.UserHomeReader;
import de.teilautos.mailing.SmtpClient;

@Deployment(resources = { "registration.bpmn", "identification-mail.bpmn" })
public class RegistrationTest {

	@Rule
	@ClassRule
	public static ProcessEngineRule processEngine = TestCoverageProcessEngineRuleBuilder.create().build();

	private RuntimeService runtimeService = null;
	@SuppressWarnings("unused")
	private TaskService taskService = null;

	@Before
	public void setUp() throws Exception {
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
	}

	private void sendRegistrationMail() throws IOException {
		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");
		SmtpClient registrationMailSender = new SmtpClient("pop.gmail.com", "teilautos.jimdo@gmail.com",
				AesEncrypter.decrypt("D/WalIvz3r0flg2miGSd/8EtPN6EqZa61OBmsRM/Iwg=", secretKey));
		String subject = "Nachricht über https://www.teilautos.de/registrieren/";
		registrationMailSender.setSubject(subject);
		String content = new FileReader().readFile("registration-mail.txt");
		registrationMailSender.setContent(content);
		String registrationMailTo = "teilautos.test@gmail.com";
		registrationMailSender.send(registrationMailTo);
	}

	@Test
	public void testSendRegistrationMail() throws IOException {
		sendRegistrationMail();
	}
	
	@Test
	public void test() throws IOException {
		sendRegistrationMail();

		VariableMap variables = Variables.createVariables();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_RegistrationCheck",
				variables);

		assertThat(processInstance).isEnded();
	}

}
