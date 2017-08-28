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
package de.teilautos.mailing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import de.teilautos.registration.RegistrationMailModel;

public class MailClient {

	protected String host;
	protected String username;
	protected String password;

	private Properties properties = new Properties();
	private Store store;
	private Folder folder;

	public MailClient(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
		
		properties.put("mail.pop3.host", host);
		properties.put("mail.pop3.port", "995");
		properties.put("mail.pop3.starttls.enable", "true");

	}

	public MailClient openInbox() throws MessagingException {
		Session session = Session.getInstance(properties);

		store = session.getStore("pop3s");
		store.connect(host, username, password);
		folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		
		return this;
	}

	public Collection<RegistrationMailModel> receiveMessages() throws MessagingException, IOException {
		Session session = Session.getInstance(properties);
		Store store = session.getStore("pop3s");
		store.connect(host, username, password);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);

		Collection<RegistrationMailModel> registrationMailModels = new ArrayList<RegistrationMailModel>();
		
		Message[] messages = folder.getMessages();
		for (Message message : messages) {
			RegistrationMailModel model = new RegistrationMailModel(message.getSubject(),
					(String) message.getContent());
			registrationMailModels.add(model);
		}

		folder.close(false);
		store.close();

		return registrationMailModels;
	}

	public void sendMessage() {
		
	}
	
	public void closeInbox() throws MessagingException {
		folder.close(false);
		store.close();
	}

}
