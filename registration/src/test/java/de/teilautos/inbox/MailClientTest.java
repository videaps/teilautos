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
package de.teilautos.inbox;

import java.util.Collection;

import org.junit.Test;

import de.teilautos.registration.RegistrationMailModel;

public class MailClientTest {

	@Test
	public void gmail() throws Exception {

		String host = "pop.gmail.com";
		String username = "teilautos.registrierung@gmail.com";
		String password = "iX6F4kI6BvP7F0ddxnK5";

		MailClient client = new MailClient(host, username, password);
		Collection<RegistrationMailModel> messages = client.receiveMessages();

		System.out.println("messages.length---" + messages.size());

		for (RegistrationMailModel model : messages) {
			System.out.println("---------------------------------");
			System.out.println("Subject: " + model.getSubject());
			System.out.println("Text: " + model.getContent());
		}

	}

}
