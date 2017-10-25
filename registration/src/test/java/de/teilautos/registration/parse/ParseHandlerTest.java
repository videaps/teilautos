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
package de.teilautos.registration.parse;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.teilautos.io.FileReader;
import de.teilautos.registration.parse.ParseHandler;
import de.teilautos.registration.parse.PartnerProgramEnum;
import de.teilautos.registration.parse.RegistrationModel;

public class ParseHandlerTest {

	@Test
	public void parseContent() throws IOException {
		String content = new FileReader().readFile("registration-mail.txt");
		ParseHandler handler = new ParseHandler();
		RegistrationModel model = handler.parseContent(content);
		
		assertEquals("Oliver", model.getFirstname());
		assertEquals("Hock", model.getSurname());
		assertEquals("oliver.hock@gmail.com", model.getEmail());
		assertEquals("Bergstr. 12", model.getStreetNo());
		assertEquals("59269 Beckum", model.getPostCode());
		assertEquals("0176 / 29499727", model.getPhone());
		
		assertEquals(PartnerProgramEnum.None, model.getPartnerProgram());
		assertEquals("", model.getReference());
		
		assertTrue(model.isTermsAndConditionsAccepted());
		assertTrue(model.isDataPrivacyAccepted());
		assertFalse(model.isNewsletterWanted());
	}

}
