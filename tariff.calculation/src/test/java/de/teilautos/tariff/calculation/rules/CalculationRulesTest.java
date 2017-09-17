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
package de.teilautos.tariff.calculation.rules;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openl.rules.runtime.RulesEngineFactory;

import de.teilautos.tariff.calculation.domains.CarCost;
import de.teilautos.tariff.calculation.domains.CarType;
import de.teilautos.tariff.calculation.domains.Price;
import de.teilautos.tariff.calculation.domains.TariffType;

public class CalculationRulesTest {
	
	private final RulesEngineFactory<CalculationRules> rulesEngine = new RulesEngineFactory<CalculationRules>(
			"src/main/resources/CalculationRules.xls", CalculationRules.class);
	private CalculationRules rules = (CalculationRules) rulesEngine.newInstance();

	@Test
	public void price() {
		Price price = rules.getPriceByTariff(TariffType.GELEGENTLICH.getName());
		
		Float hourlyRate = price.getHourlyRate();
		assertNotNull(hourlyRate);
		
		Float kilometerPrice = price.getKilometerPrice();
		assertNotNull(kilometerPrice);
		
		Float monthlyRate = price.getMonthlyRate();
		assertNotNull(monthlyRate);
	}

	@Test
	public void carCost() {
		CarCost carCost = rules.getCarCostByType(CarType.KOMPAKTKLASSE.getName());
		
		Float listPrice = carCost.getListPrice();
		assertNotNull(listPrice);
	}
}
