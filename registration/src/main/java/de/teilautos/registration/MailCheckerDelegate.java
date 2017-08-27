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

import java.util.ArrayList;
import java.util.Collection;

import javax.mail.Message;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.teilautos.inbox.MailChecker;

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

		Message[] messages = new MailChecker(hostValue, usernameValue, passwordValue).execute();
		
		Collection<RegistrationMailModel> registrationMailModels = new ArrayList<RegistrationMailModel>(); 
		for (Message message : messages) {
			RegistrationMailModel model = new RegistrationMailModel(message.getSubject(), (String) message.getContent());
			registrationMailModels.add(model);
		}
		
		execution.setVariable("registrationMailModels", registrationMailModels);
		
		if (logger.isTraceEnabled()) {
			logger.trace("messages.length=" + (messages != null ? messages.length : 0));
			
			System.out.println("messages.length=" + (messages != null ? messages.length : 0));
			
			for (Message message : messages) {
				logger.trace("subject=" + message.getSubject());
				logger.trace("content=" + message.getContent());
				
				System.out.println("subject=" + message.getSubject());
				System.out.println("content=" + message.getContent());
			}
		}
		
		logger.info("exiting");
	}

}
