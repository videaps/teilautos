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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

import de.teilautos.encryption.AesEncrypter;
import de.teilautos.io.FileReader;
import de.teilautos.io.UserHomeReader;

public class MailingTest {

	private SmtpClient registrationMailSender;
	private String registrationMailTo = "teilautos.test@gmail.com";

	private ImapClient registrationMailReceiver;

	@Before
	public void setUp() throws Exception {
		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");

		registrationMailSender = new SmtpClient("pop.gmail.com", "teilautos.jimdo@gmail.com",
				AesEncrypter.decrypt("D/WalIvz3r0flg2miGSd/8EtPN6EqZa61OBmsRM/Iwg=", secretKey));

		registrationMailReceiver = new ImapClient("imap.gmail.com", "teilautos.test@gmail.com",
				AesEncrypter.decrypt("D/WalIvz3r0flg2miGSd/8EtPN6EqZa61OBmsRM/Iwg=", secretKey));
	}

	@Test
	public void test() throws IOException, MessagingException, InterruptedException {
		registrationMailReceiver.open();
		registrationMailReceiver.deleteMessages();
		registrationMailReceiver.close();
		
		int n = 3;
		sendRegistrationMails(n);

		registrationMailReceiver.open();
		Message[] messages = registrationMailReceiver.fetchUnreadMessages(); 
		assertEquals(n, messages.length);
		
		for(int i = 0; i < messages.length-1; i++) {
			Object content = messages[i].getContent();
			assertNotNull(content);
		}
		registrationMailReceiver.close();
		
		registrationMailReceiver.open();
		messages = registrationMailReceiver.fetchUnreadMessages();
		assertEquals(1, messages.length);
		registrationMailReceiver.close();

		registrationMailReceiver.open();
		messages = registrationMailReceiver.fetchUnreadMessages();
		assertEquals(1, messages.length);
		Object content = messages[0].getContent();
		assertNotNull(content);
		registrationMailReceiver.close();
		
		registrationMailReceiver.open();
		messages = registrationMailReceiver.fetchUnreadMessages();
		assertEquals(0, messages.length);
		registrationMailReceiver.close();

		registrationMailReceiver.open();
		registrationMailReceiver.deleteMessages();
		registrationMailReceiver.close();
		
	}

	public void sendRegistrationMails(int n) throws IOException {
		String subject = "Nachricht über https://www.teilautos.de/registrieren/";
		registrationMailSender.setSubject(subject);

		String content = new FileReader().readFile("registration-mail.txt");
		registrationMailSender.setContent(content);

		for (int i = 0; i < n; i++) {
			registrationMailSender.send(registrationMailTo);
		}
	}

}
