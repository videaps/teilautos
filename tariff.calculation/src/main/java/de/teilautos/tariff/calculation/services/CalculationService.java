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

import org.openl.rules.runtime.RulesEngineFactory;

import de.teilautos.tariff.calculation.rules.CalculationRules;
import de.teilautos.tariff.calculation.rules.Price;

/**
 *
 */
public class CalculationService {
	private final RulesEngineFactory<CalculationRules> rulesEngine = new RulesEngineFactory<CalculationRules>(
			"src/main/resources/CalculationRules.xls", CalculationRules.class);
	private CalculationRules rules = (CalculationRules) rulesEngine.newInstance();

	/**
	 * 
	 * @param kilometer
	 * @return
	 */
	public String getTariffName(int kilometer) {
		String name = rules.getTariffByDistance(kilometer);
		return name;
	}

	/**
	 * 
	 * @param tariffName
	 * @return
	 */
	public Price getPrice(String tariffName) {
		Price price = rules.getPriceByTariff(tariffName);
		return price;
	}

	/**
	 * 
	 * @param kilometerPerYear
	 * @param price
	 * @param kilometerPerHour
	 * @return
	 */
	public float yearlyCostOccupant(String kilometerPerYear, Price price, String kilometerPerHour) {
		float yearlyContribution = yearlyContribution(price.getMonthlyRate());
		float hourlyRatesPerYear = hourlyRatesPerYear(kilometerPerYear, kilometerPerHour, price.getHourlyRate());
		float kilometerPricesPerYear = kilometerPricesPerYear(kilometerPerYear, price.getKilometerPrice());
		
		float yearlyCost = yearlyContribution + hourlyRatesPerYear + kilometerPricesPerYear;
		
		return yearlyCost;
	}

	/*
	 * @param kilometerPerYear
	 * @param kilometerPrice
	 * @return
	 */
	public float kilometerPricesPerYear(String kilometerPerYear, String kilometerPrice) {
		int kmYear = Integer.valueOf(kilometerPerYear);
		float kmPrice = Float.valueOf(kilometerPrice);
		
		float priceYear = kmYear * kmPrice;
		
		return priceYear;
	}

	/*
	 * @param kilometerPerYear
	 * @param kilometerPerHour
	 * @param hourlyRate
	 * @return
	 */
	public float hourlyRatesPerYear(String kilometerPerYear, String kilometerPerHour, String hourlyRate) {
		int kmYear = Integer.valueOf(kilometerPerYear);
		float kmHour = Float.valueOf(kilometerPerHour);
		float rateHour = Float.valueOf(hourlyRate);

		float rateYear = ( kmYear / kmHour ) * rateHour;
		
		return rateYear;
	}

	/*
	 * @param monthlyRate
	 * @return
	 */
	public float yearlyContribution(String monthlyRate) {
		float rate = Float.valueOf(monthlyRate);
		
		float yearlyContribution = 12 * rate;
		
		return yearlyContribution;
	}
	
}
