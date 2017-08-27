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

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfirmationMailParserTest {
	private static final String content = "Hallo,\n\n du hast eine Nachricht über deine Jimdo-Seite https://www.teilautos.de/registrieren/ erhalten:\n\n -------------------------------------\n\n Vorname: Oliver Ludger\n\n Nachname: Hock\n\n E-Mail: oliver.hock@gmail.com\n\n E-Mail (Wiederholung): oliver.hock@gmail.com\n\n Adresse (Straße, Nr., PLZ, Ort): Bergstraße 12\n 59269 Beckum\n\n Telefon / Handy: 0176 29499727\n\n Zum Newsletter anmelden (auf dem Laufenden bleiben): Ja\n\n Partnerprogramm: Abo RVM (mit Kundennummer)\n\n Kundennummer: 13081971\n\n Ich akzeptiere die Allgemeinen Geschäftsbedingungen (diese stehen unten am Ende dieser Seite): Ja\n\n Ich stimme der Datenschutzerklärung zu (diese stehen unten am Ende dieser Seite): Ja";

	@Test
	public void email() {
		ConfirmationMailParser parser = new ConfirmationMailParser(content);
		
		String email = parser.findEmail();
		assertEquals("oliver.hock@gmail.com", email);
	}

	@Test
	public void firstname() {
		ConfirmationMailParser parser = new ConfirmationMailParser(content);
		
		String firstname = parser.findFirstname();
		assertEquals("Oliver Ludger", firstname);
	}

}
