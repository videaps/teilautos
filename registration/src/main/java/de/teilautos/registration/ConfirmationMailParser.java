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

public class ConfirmationMailParser {

	private String content = null;

	public ConfirmationMailParser(String content) {
		this.content = content;
	}

	public String findEmail() {
		String result = find("E-Mail: ");
		return result;
	}

	public String findFirstname() {
		String result = find("Vorname: ");
		return result;
	}
	
	private String find(String key) {
		int startIndex = content.indexOf(key);
		String firstnameSub = content.substring(startIndex);
		
		int nextLineSeperatorIndex = firstnameSub.indexOf("\n\n");
		String firstname = firstnameSub.substring(key.length(), nextLineSeperatorIndex);
		
		return firstname;
	}
}
