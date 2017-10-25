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

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.teilautos.encryption.AesEncrypter;
import de.teilautos.io.FileReader;
import de.teilautos.io.UserHomeReader;

public class SmtpClientTest {

	private String host = "pop.gmail.com";
	private String username = "teilautos.jimdo@gmail.com";
	private String to = "teilautos.test@gmail.com";
	private String subject;
	private String content;

	@Before
	public void readContent() throws IOException {
		subject = System.currentTimeMillis() + " Nachricht über https://www.teilautos.de/registrieren/";
		content = new FileReader().readFile("registration-mail.txt");
	}

	@Test
	public void test() throws IOException {
		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");
		String password = AesEncrypter.decrypt("D/WalIvz3r0flg2miGSd/8EtPN6EqZa61OBmsRM/Iwg=", secretKey);

		SmtpClient mailSender = new SmtpClient(host, username, password);
		mailSender.setSubject(subject);
		mailSender.setContent(content);
		mailSender.send(to);

		assertTrue(true);
	}

}
