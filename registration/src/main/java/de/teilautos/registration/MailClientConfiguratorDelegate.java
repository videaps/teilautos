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
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailClientConfiguratorDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(MailClientConfiguratorDelegate.class);

	public void execute(DelegateExecution execution) throws Exception {
		logger.info("entering");

		execution.setVariable("host", "pop.gmail.com");
		execution.setVariable("username", "teilautos.registrierung@gmail.com");
		execution.setVariable("password", "iX6F4kI6BvP7F0ddxnK5");
		execution.setVariable("from", "registrierung@teilautos.de");
		execution.setVariable("bcc", "oliver.hock@teilautos.de");
		
		if (logger.isTraceEnabled()) {
			logger.trace("host=" + execution.getVariable("host"));
			logger.trace("username=" + execution.getVariable("username"));
			logger.trace("password=" + "********");
			logger.trace("from=" + execution.getVariable("from"));
			logger.trace("bcc=" + execution.getVariable("bcc"));
		}

		logger.info("exiting");
	}

}
