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

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

public class Beautifier {

	public static String kilometer(Double value) {
		BigDecimal bd = new BigDecimal(value).setScale(0, RoundingMode.HALF_DOWN);
		String result = bd.toPlainString();
		return result;
	}
	
	public static String euros(double amount) {
		BigDecimal bd = new BigDecimal(amount).setScale(0, RoundingMode.HALF_DOWN);
		String result = bd.toPlainString() + " €";
		return result;
	}
	
	public static String padKilometer(String kilometer, String maxKilometer) {
		int size = maxKilometer.length();
		String result = StringUtils.leftPad(kilometer, size, " ");
		return result;
	}

	public static String kilometerUnit(String kilometer) {
		String result = kilometer + " km";
		return result;
	}
}
