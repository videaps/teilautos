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

import de.teilautos.tariff.calculation.domains.CarCost;
import de.teilautos.tariff.calculation.domains.DetailCarCost;
import de.teilautos.tariff.calculation.domains.Price;
import de.teilautos.tariff.calculation.rules.CalculationRules;

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
	 * @param carTypeName
	 * @return
	 */
	public CarCost getCarCost(String carTypeName) {
		CarCost carCost = rules.getCarCostByType(carTypeName);
		return carCost;
	}

	/**
	 * 
	 * @param kilometerPerYear
	 * @param price
	 * @param kilometerPerHour
	 * @return
	 */
	public float yearlyCostCarsharing(Integer kilometerPerYear, Price price, Float kilometerPerHour) {
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
	public float kilometerPricesPerYear(Integer kilometerPerYear, Float kilometerPrice) {
		float priceYear = kilometerPerYear * kilometerPrice;
		return priceYear;
	}

	/*
	 * @param kilometerPerYear
	 * @param kilometerPerHour
	 * @param hourlyRate
	 * @return
	 */
	public float hourlyRatesPerYear(Integer kilometerPerYear, Float kilometerPerHour, Float hourlyRate) {
		float rateYear = ( kilometerPerYear / kilometerPerHour ) * hourlyRate;
		return rateYear;
	}

	/*
	 * @param monthlyRate
	 * @return
	 */
	public float yearlyContribution(Float monthlyRate) {
		return 12 * monthlyRate;
	}

	public DetailCarCost yearlyDetailCostOwnCar(String carType, Integer kilometerPerYear) {
		CarCost carCost = rules.getCarCostByType(carType);

		DetailCarCost detailCarCost = new DetailCarCost();
		
		detailCarCost.setFixCost(12 * carCost.getMonthlyFixCost());
		detailCarCost.setGarageCost(12 * carCost.getMonthlyGarageCost());
		detailCarCost.setValueLoss(12 * carCost.getMonthlyValueLoss());
		
		float consumptionCost = kilometerPerYear * carCost.getConsumptionCost();
		detailCarCost.setOperationsCost(consumptionCost);
		
		return detailCarCost;
	}
	/**
	 * 
	 * @param carType
	 * @param kilometerPerYear
	 * @return
	 */
	public float yearlyTotalCostOwnCar(String carType, Integer kilometerPerYear) {
		CarCost carCost = rules.getCarCostByType(carType);
		
		float consumptionCost = kilometerPerYear * carCost.getConsumptionCost();
		float totalCost = 12 * ( carCost.getMonthlyFixCost() + carCost.getMonthlyGarageCost() + carCost.getMonthlyValueLoss() ) + consumptionCost ;
		
		return totalCost;
	}
}
