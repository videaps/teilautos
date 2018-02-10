/*
 Copyright (c) 2018 Videa Project Services GmbH

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
package de.teilautos.fahrtkostenrechner;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TariffServiceTest {

	private TariffService service = new TariffService();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void gelegentlich() {
		TariffModel tariff = service.getTariff("Gelegentlich");
		assertEquals(2.7, tariff.getHourlyPrice(), 0.1);
		assertEquals(0.32, tariff.getKilometerPrice(), 0.01);
	}

	@Test
	public void nomal() {
		TariffModel tariff = service.getTariff("Normal");
		assertEquals(1.7, tariff.getHourlyPrice(), 0.1);
		assertEquals(0.24, tariff.getKilometerPrice(), 0.01);
	}

	@Test
	public void viel() {
		TariffModel tariff = service.getTariff("Viel");
		assertEquals(1.2, tariff.getHourlyPrice(), 0.1);
		assertEquals(0.16, tariff.getKilometerPrice(), 0.01);
	}

}
