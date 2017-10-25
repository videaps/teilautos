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
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import com.sun.mail.imap.IMAPFolder;

public class ImapClient {
	private IMAPFolder folder = null;
	private Store store = null;

	protected String host;
	protected String username;
	protected String password;

	public ImapClient(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}

	/**
	 * Fetch unread messages from inbox and set all fetched messages as read.
	 * Messages are left in the inbox whatsoever.
	 * 
	 * @return Collection of fetched unread e-mails
	 * @throws MessagingException
	 * @throws IOException
	 */
	public Message[] fetchUnreadMessages() throws MessagingException {
		Message[] messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
		return messages;
	}

	public void setAsRead(Message[] messages) throws MessagingException {
		for (Message message : messages) {
			message.setFlag(Flag.SEEN, true);
		}
	}

	public void deleteMessages() throws MessagingException {
		Message[] messages = folder.getMessages();
		for (Message message : messages) {
			message.setFlag(Flags.Flag.DELETED, true);
		}
	}

	public void open() throws NoSuchProviderException, MessagingException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);

		store = session.getStore("imaps");
		if (!store.isConnected()) {
			store.connect(host, username, password);
		}

		folder = (IMAPFolder) store.getFolder("inbox");
		if (!folder.isOpen()) {
			folder.open(Folder.READ_WRITE);
		}
	}

	public void close() throws MessagingException {
		if (folder != null && folder.isOpen()) {
			folder.close(true);
		}
		if (store != null) {
			store.close();
		}
	}

}
