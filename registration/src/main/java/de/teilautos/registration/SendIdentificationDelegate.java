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

import de.teilautos.encryption.AesEncrypter;
import de.teilautos.io.UserHomeReader;
import de.teilautos.mailing.EmailModel;
import de.teilautos.mailing.SmtpClient;
import de.teilautos.registration.parse.RegistrationModel;

public class SendIdentificationDelegate implements JavaDelegate {
	private static final Logger logger = Logger.getLogger(SendIdentificationDelegate.class);

	public void execute(DelegateExecution execution) throws Exception {
		logger.trace("begin");
		
		MailServerConfigModel identificationServerConfig = (MailServerConfigModel) execution
				.getVariable("identificationServerConfig");
		EmailModel identificationMail = (EmailModel) execution.getVariable("identificationMail");
		RegistrationModel registrationModel = (RegistrationModel) execution.getVariable("registrationModel");
		
		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");
		String password = AesEncrypter.decrypt(identificationServerConfig.getPassword(), secretKey);

		SmtpClient mailSender = new SmtpClient(identificationServerConfig.getHost(),
				identificationServerConfig.getUsername(), password);
		mailSender.setSubject(identificationMail.getSubject());
		mailSender.setContent(identificationMail.getContent());
		
		mailSender.send(registrationModel.getEmail());
		
		logger.trace("end");
	}

}
