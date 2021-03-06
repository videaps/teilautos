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
package de.teilautos.tariff.calculation.ui;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeautifierTest {

	@Test
	public void kilometer() {
		Double double1 = new Double("1430.453534");
		String value = Beautifier.kilometer(double1);
		assertEquals("1430", value);
	}
	
	@Test
	public void padKilometer() {
		String maxValue = "10000";
		
		String result = Beautifier.padKilometer("10000", maxValue);
		assertEquals("10000", result);

		assertEquals(" 9500", Beautifier.padKilometer("9500", maxValue));
		assertEquals(" 1000", Beautifier.padKilometer("1000", maxValue));
		assertEquals("  999", Beautifier.padKilometer("999", maxValue));
		assertEquals("    0", Beautifier.padKilometer("0", maxValue));
	}

}
