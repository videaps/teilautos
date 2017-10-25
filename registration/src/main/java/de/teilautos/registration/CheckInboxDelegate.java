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
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.teilautos.encryption.AesEncrypter;
import de.teilautos.io.UserHomeReader;
import de.teilautos.mailing.EmailModel;
import de.teilautos.mailing.ImapClient;

public class CheckInboxDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		MailServerConfigModel mailServerConfig = (MailServerConfigModel) execution
				.getVariable("registrationServerConfig");

		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");
		String decryptedPassword = AesEncrypter.decrypt(mailServerConfig.getPassword(), secretKey);

		ImapClient client = new ImapClient(mailServerConfig.getHost(), mailServerConfig.getUsername(),
				decryptedPassword);
		client.open();
		Message[] messages = client.fetchUnreadMessages();
		Collection<EmailModel> newRegistrationEmails = new ArrayList<EmailModel>();
		for (Message message : messages) {
			String subject = message.getSubject();
			
			// FIXME replace hard coded string by configuration from outside and check if content is text 
			if("Nachricht über https://www.teilautos.de/registrieren/".equals(subject)) {
			
				String content = message.getContent().toString();
				EmailModel email = new EmailModel(subject, content);
				newRegistrationEmails.add(email);
			}
		}
		client.close();

		execution.setVariable("newRegistrationEmails", newRegistrationEmails);
	}

}
