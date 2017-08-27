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

import static org.junit.Assert.*;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Deployment(resources = { "registration-mail-verification.dmn" })
public class RegistrationMailVerificationTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void registrationMail() {
		VariableMap variables = Variables.createVariables().putValue("subject", "Nachricht über https://www.teilautos.de/registrieren/");

		DmnDecisionTableResult decisionResult = processEngine.getDecisionService()
				.evaluateDecisionTableByKey("registrationMailVerification", variables);

		assertEquals(true, decisionResult.getSingleEntry());
	}
	
	@Test
	public void anyOtherMail() {
		VariableMap variables = Variables.createVariables().putValue("subject", "Teilautos JUnit Test");

		DmnDecisionTableResult decisionResult = processEngine.getDecisionService()
				.evaluateDecisionTableByKey("registrationMailVerification", variables);

		assertEquals(1, decisionResult.getResultList().size());
		assertEquals(false, decisionResult.getSingleResult().getEntry("registrationMail"));
	}

}
