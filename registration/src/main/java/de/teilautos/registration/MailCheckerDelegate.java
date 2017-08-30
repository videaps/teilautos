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

import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.teilautos.encryption.AesEncrypter;
import de.teilautos.io.UserHomeReader;
import de.teilautos.mailing.MailChecker;

public class MailCheckerDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(MailCheckerDelegate.class);

	private Expression host;
	private Expression username;
	private Expression password;

	public void execute(DelegateExecution execution) throws Exception {
		logger.trace("entering");
		
		String hostValue = (String) host.getValue(execution);
		String usernameValue = (String) username.getValue(execution);
		String passwordValue = (String) password.getValue(execution);

		if (logger.isTraceEnabled()) {
			logger.debug("host=" + hostValue);
			logger.debug("username=" + usernameValue);
			logger.debug("password="+"********");
		}

		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");
		String decryptedPassword = AesEncrypter.decrypt(passwordValue, secretKey);
		
		logger.info("checking mail for registration");
		MailChecker client = new MailChecker(hostValue, usernameValue, decryptedPassword);
		logger.info("done");
		
		Collection<RegistrationMailModel> registrationMailModels = client.execute();
		execution.setVariable("registrationMailModels", registrationMailModels);
		
		if (logger.isTraceEnabled()) {
			logger.debug("messages.length=" + (registrationMailModels != null ? registrationMailModels.size() : 0));
			
			for (RegistrationMailModel message : registrationMailModels) {
				logger.debug("subject=" + message.getSubject());
				logger.debug("content=" + message.getContent());
			}
		}
		
		logger.trace("exiting");
	}

}
