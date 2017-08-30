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

import de.teilautos.io.FileReader;

public class IdentificationMailCreatorDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(IdentificationMailCreatorDelegate.class);

	private Expression firstname;

	public void execute(DelegateExecution execution) throws Exception {
		logger.trace("entering");

		String firstnameValue = (String) firstname.getValue(execution);
		if (logger.isTraceEnabled()) {
			logger.debug("firstname="+firstnameValue);
		}
		
		logger.info("creating identification mail for user: "+firstnameValue);
		
		FileReader fileReader = new FileReader();
		String subject = fileReader.readFile("identification-mail-subject.txt");
		String content = fileReader.readFile("identification-mail-content.txt");

		logger.info("done");
		
		execution.setVariable("identificationMailSubject", subject);
		execution.setVariable("identificationMailContent", content);

		if (logger.isTraceEnabled()) {
			logger.debug("identificationMailSubject=" + subject);
			logger.debug("identificationMailContent=" + content);
		}

		logger.trace("exiting");
	}

}
