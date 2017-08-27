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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadUsersDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(ReadUsersDelegate.class);

	private FixedValue organisation;

	public void execute(DelegateExecution execution) throws Exception {
		logger.trace("Entering " + ReadUsersDelegate.class.getName());
		if (logger.isTraceEnabled()) {
			for (String variableName : execution.getVariableNames()) {
				Object variableValue = execution.getVariable(variableName);
				logger.trace(variableName + "=" + variableValue);
			}
		}

		String organisationValue = (String) organisation.getValue(execution);
		logger.trace("organisationValue=" + organisationValue);

		// TODO implement REST call to Zemtu
		Collection<User> users = readUsersFromZemtu();
		if (logger.isTraceEnabled()) {
			logger.trace("users=" + users);
		}
		execution.setVariable("users", users);

		if (logger.isTraceEnabled()) {
			for (String variableName : execution.getVariableNames()) {
				Object variableValue = execution.getVariable(variableName);
				logger.trace(variableName + "=" + variableValue);
			}
		}
		logger.trace("Exiting " + ReadUsersDelegate.class.getName());
	}

	private Collection<User> readUsersFromZemtu() {
		Collection<User> users = new ArrayList<User>();

		User user = new User();
		user.setId(13342);
		user.setUsername("oliver");
		user.setEmail("oliver.hock@gmail.com");
		user.setFirstname("Oliver");
		user.setSurname("Hock");
		user.setActive(true);
		user.setLastLogin(Instant.parse("2017-07-09T18:42:00.00Z"));
		user.setRegistrationDate(Instant.parse("2017-02-09T20:09:00.00Z"));

		users.add(user);

		return users;
	}

}
