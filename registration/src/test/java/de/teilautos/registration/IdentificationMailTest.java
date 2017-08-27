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
//import static org.junit.Assert.*;

import org.camunda.bpm.engine.RuntimeService;
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

@Deployment(resources = { "identification-mail.bpmn", "registration-mail-verification.dmn" })
public class IdentificationMailTest {

	@Rule
	@ClassRule
	public static ProcessEngineRule processEngine = TestCoverageProcessEngineRuleBuilder.create().build();

	private RuntimeService runtimeService = null;

	@Before
	public void setUp() throws Exception {
		runtimeService = processEngine.getRuntimeService();
	}

	@Test
	public void test() {
		String subject = "Nachricht über https://www.teilautos.de/registrieren/";
		String content = "Hallo,\n\n du hast eine Nachricht über deine Jimdo-Seite https://www.teilautos.de/registrieren/ erhalten:\n\n -------------------------------------\n\n Vorname: Oliver Ludger\n\n Nachname: Hock\n\n E-Mail: oliver.hock@gmail.com\n\n E-Mail (Wiederholung): oliver.hock@gmail.com\n\n Adresse (Straße, Nr., PLZ, Ort): Bergstraße 12\n 59269 Beckum\n\n Telefon / Handy: 0176 29499727\n\n Zum Newsletter anmelden (auf dem Laufenden bleiben): Ja\n\n Partnerprogramm: Abo RVM (mit Kundennummer)\n\n Kundennummer: 13081971\n\n Ich akzeptiere die Allgemeinen Geschäftsbedingungen (diese stehen unten am Ende dieser Seite): Ja\n\n Ich stimme der Datenschutzerklärung zu (diese stehen unten am Ende dieser Seite): Ja";

		RegistrationMailModel model = new RegistrationMailModel(subject, content);

		VariableMap variables = Variables.createVariables().putValue("host", "secure.emailsrvr.com")
				.putValue("username", "oliver.hock@teilautos.de").putValue("password", "czChrwk6!")
				.putValue("from", "kontakt@teilautos.de").putValue("bcc", "oliver.hock@teilautos.de")
				.putValue("registrationMail", model);

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_IdentificationMail",
				variables);

		assertThat(processInstance).isEnded();
	}

}
