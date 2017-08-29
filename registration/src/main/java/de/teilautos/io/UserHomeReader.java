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
package de.teilautos.io;

import java.io.BufferedReader;
import java.io.IOException;

public class UserHomeReader {
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String FILE_SEPERATOR = System.getProperty("file.separator");
	
	/**
	 * This methods gets the secret key file from users home directory and reads the first line of it. 
	 * The secret key file shall contain only one string in the first line reflecting the key.
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public String readSecretKey(String fileName) throws IOException {
		java.io.FileReader fileReader = new java.io.FileReader(USER_HOME+FILE_SEPERATOR+fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String secretKey = bufferedReader.readLine().trim();
		
		bufferedReader.close();
		fileReader.close();

		return secretKey;
	}
}
