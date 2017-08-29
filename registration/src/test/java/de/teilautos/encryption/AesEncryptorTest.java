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
package de.teilautos.encryption;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.teilautos.io.UserHomeReader;

public class AesEncryptorTest {

	@Test
    public void main() throws IOException {
    	String password = "<PUT PASSWORD HERE>";
		String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");
    	String encryptedPassword = AesEncrypter.encrypt(password, secretKey);
    	System.out.println(encryptedPassword);
    }
    
    

	@Test
	public void enryptDecrypt() throws IOException {
		final String secretKey = new UserHomeReader().readSecretKey("teilautos-registrierung-secret.key");

		String originalString = "Oliver";
		
		String encryptedString = AesEncrypter.encrypt(originalString, secretKey);
		assertEquals("RE/oWlnAciHM9ixZwbOv4g==", encryptedString);
		
		String decryptedString = AesEncrypter.decrypt(encryptedString, secretKey);
		assertEquals("Oliver", decryptedString);
	}

}
