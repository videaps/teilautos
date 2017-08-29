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

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.teilautos.io.FileReader;

public class FileReaderTest {

	@Test
	public void subject() throws IOException {
		FileReader fileReader = new FileReader();
		String fileContent = fileReader.readFile("identification-mail-subject.txt");
		assertEquals("Deine Identifizierung für \"Teilautos - Das regionale Carsharing\"", fileContent);
	}
	
	@Test
	public void content() throws IOException {
		FileReader fileReader = new FileReader();
		String fileContent = fileReader.readFile("identification-mail-content.txt");
		assertTrue("Deine Identifizierung für \"Teilautos - Das regionale Carsharing\"", fileContent.startsWith("Hallo und herzlich Willkommen bei Teilautos."));
		System.out.println(fileContent);
	}

}
