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
package de.teilautos.tariff.calculation.domains;

/**
 * 
 */
public class Price {

	private Float hourlyRate;
	private Float kilometerPrice;
	private Float monthlyRate;

	public Price(Float hourlyRate, Float kilometerPrice, Float monthlyRate) {
		super();
		this.hourlyRate = hourlyRate;
		this.kilometerPrice = kilometerPrice;
		this.monthlyRate = monthlyRate;
	}

	@Override
	public String toString() {
		return "Price [hourlyRate=" + hourlyRate + ", kilometerPrice=" + kilometerPrice + ", monthlyRate=" + monthlyRate
				+ "]";
	}

	public Float getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Float getKilometerPrice() {
		return kilometerPrice;
	}

	public void setKilometerPrice(Float kilometerPrice) {
		this.kilometerPrice = kilometerPrice;
	}

	public Float getMonthlyRate() {
		return monthlyRate;
	}

	public void setMonthlyRate(Float monthlyRate) {
		this.monthlyRate = monthlyRate;
	}

}
