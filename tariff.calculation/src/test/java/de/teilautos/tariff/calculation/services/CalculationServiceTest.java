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
package de.teilautos.tariff.calculation.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import de.teilautos.tariff.calculation.domains.CarCost;
import de.teilautos.tariff.calculation.domains.CarType;
import de.teilautos.tariff.calculation.domains.Price;
import de.teilautos.tariff.calculation.services.CalculationService;

public class CalculationServiceTest {
	private CalculationService calculationService = new CalculationService();

	@Test
	public void distanceTariffName() {
		String tariff = calculationService.getTariffName(1000);
		assertEquals("Normal", tariff);
	}

	@Test
	public void tariffPrice() {
		Price price = calculationService.getPrice("Normal");

		BigDecimal monthlyRate = new BigDecimal(price.getMonthlyRate()).setScale(2, RoundingMode.HALF_UP);
		assertEquals("7.50", monthlyRate.toPlainString());

		BigDecimal hourlyRate = new BigDecimal(price.getHourlyRate()).setScale(2, RoundingMode.HALF_UP);
		assertEquals("1.70", hourlyRate.toString());

		BigDecimal kilometerPrice = new BigDecimal(price.getKilometerPrice()).setScale(2, RoundingMode.HALF_UP);
		assertEquals("0.30", kilometerPrice.toPlainString());
	}

	@Test
	public void hourlyRatesPerYear() {
		double rate = calculationService.hourlyRatesPerYear(1000, 7.5f, 1.70f);
		assertEquals(226.67f, rate, 0.01);
	}

	@Test
	public void kilometerPricesPerYear() {
		float price = calculationService.kilometerPricesPerYear(1000, 0.30f);
		assertEquals(300.00f, price, 0.01);
	}
	
	@Test
	public void carTypeCost() {
		CarCost carCost = calculationService.getCarCost(CarType.KLEINSTWAGEN.getName());
		assertEquals(Float.valueOf(73), carCost.getMonthlyFixCost());
	}

	@Test
	public void yearlyContribution() {
		float contribution = calculationService.yearlyContribution(7.50f);
		assertEquals(90.00f, contribution, 0.01);
	}

	@Test
	public void yearlyCostCarsharing() {
		Price price = calculationService.getPrice("Normal");
		double cost = calculationService.yearlyCostCarsharing(1000, price, 7.5);
		assertEquals(616.66, cost, 0.01);
	}

	@Test
	public void yearlyCostOwnCar() {
		float cost = calculationService.yearlyTotalCostOwnCar(CarType.KOMPAKTKLASSE.getName(), 1000);
		assertEquals(3666.00f, cost, 0.01);
	}

}
