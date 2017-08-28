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

import de.teilautos.mailing.MailClient;

public class MailCheckerDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(MailCheckerDelegate.class);

	private Expression host;
	private Expression username;
	private Expression password;

	public void execute(DelegateExecution execution) throws Exception {
		logger.info("entering");
		
		String hostValue = (String) host.getValue(execution);
		String usernameValue = (String) username.getValue(execution);
		String passwordValue = (String) password.getValue(execution);

		if (logger.isTraceEnabled()) {
			logger.trace("host=" + hostValue);
			logger.trace("username=" + usernameValue);
			logger.trace("password="+"********");
		}

		MailClient client = new MailClient(hostValue, usernameValue, passwordValue);
		Collection<RegistrationMailModel> registrationMailModels = client.receiveMessages();
		execution.setVariable("registrationMailModels", registrationMailModels);
		
		if (logger.isTraceEnabled()) {
			logger.trace("messages.length=" + (registrationMailModels != null ? registrationMailModels.size() : 0));
			
			for (RegistrationMailModel message : registrationMailModels) {
				logger.trace("subject=" + message.getSubject());
				logger.trace("content=" + message.getContent());
			}
		}
		
		logger.info("exiting");
	}

}
