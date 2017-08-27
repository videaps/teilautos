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

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.teilautos.inbox.MailSender;


public class MailSenderDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(MailSenderDelegate.class);

	private Expression host;
	private Expression username;
	private Expression password;
	private Expression to;
	private Expression from;
	private Expression bcc;
	
	private Expression subject;
	private Expression content;

	public void execute(DelegateExecution execution) throws Exception {
		logger.info("entering");
		
		String hostValue = (String) host.getValue(execution);
		String usernameValue = (String) username.getValue(execution);
		String passwordValue = (String) password.getValue(execution);
		String toValue = (String) to.getValue(execution);
		String fromValue = (String) from.getValue(execution);
		String bccValue = (String) bcc.getValue(execution);
		String subjectValue = (String) subject.getValue(execution);
		String contentValue = (String) content.getValue(execution);
		
		if (logger.isTraceEnabled()) {
			logger.trace("host=" + hostValue);
			logger.trace("username=" + usernameValue);
			logger.trace("to=" + toValue);
			logger.trace("from=" + fromValue);
			logger.trace("bcc="+bccValue);
			logger.trace("subject="+subjectValue);
			logger.trace("content="+contentValue);
		}

		MailSender mailSender = new MailSender(hostValue, usernameValue, passwordValue, toValue, fromValue, bccValue);
		mailSender.setSubject(subjectValue);
		mailSender.setContent(contentValue);
		
		mailSender.execute();
		
		logger.info("exiting");
	}

}
