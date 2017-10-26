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

import org.apache.log4j.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.teilautos.mailing.EmailModel;
import de.teilautos.registration.identify.IdentifyHandler;
import de.teilautos.registration.parse.RegistrationModel;

public class CreateIdentificationDelegate implements JavaDelegate {
	private static final Logger logger = Logger.getLogger(CreateIdentificationDelegate.class);

	public void execute(DelegateExecution execution) throws Exception {
		logger.trace("begin");
		
		RegistrationModel registrationModel = (RegistrationModel) execution.getVariable("registrationModel");

		IdentifyHandler handler = new IdentifyHandler();
		String firstname = registrationModel.getFirstname();
		String surname = registrationModel.getSurname();
		String username = firstname.toLowerCase() + "." + surname.toLowerCase();
		String content = handler.createContent(firstname, surname, username);
		
		String subject = "Ihre Registrierung bei \"Teilautos - Das regionale Carsharing\"";
		// FIXME get subject from config file
		EmailModel identificationMail = new EmailModel(subject, content);
		logger.debug(identificationMail);
		execution.setVariable("identificationMail", identificationMail);
		
		logger.trace("end");
	}

}
