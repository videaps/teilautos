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

public class CarCost {

	private Float listPrice;
	private Float monthlyFixCost; // Insurance and Tax
	private Float monthlyGarageCost; // Inspection/Maintenance, TuV, Fixing
	private Float monthlyOperationsCost; // Petrol
	private Float monthlyValueLoss;
	
	private Float monthlyTotalCost;
	private Float costPerKilometer;
	private Float consumptionCost;

	public CarCost(Float listPrice, Float monthlyFixCost, Float monthlyGarageCost, Float monthlyOperationsCost,
			Float monthlyValueLoss, Float monthlyTotalCost, Float costPerKilometer, Float consumptionCost) {
		super();
		this.listPrice = listPrice;
		this.monthlyFixCost = monthlyFixCost;
		this.monthlyGarageCost = monthlyGarageCost;
		this.monthlyOperationsCost = monthlyOperationsCost;
		this.monthlyValueLoss = monthlyValueLoss;
		this.monthlyTotalCost = monthlyTotalCost;
		this.costPerKilometer = costPerKilometer;
		this.consumptionCost = consumptionCost;
	}

	public Float getConsumptionCost() {
		return consumptionCost;
	}

	public void setConsumptionCost(Float consumptionCost) {
		this.consumptionCost = consumptionCost;
	}

	public Float getListPrice() {
		return listPrice;
	}

	public void setListPrice(Float listPrice) {
		this.listPrice = listPrice;
	}

	public Float getMonthlyGarageCost() {
		return monthlyGarageCost;
	}

	public void setMonthlyGarageCost(Float monthlyGarageCost) {
		this.monthlyGarageCost = monthlyGarageCost;
	}

	public Float getMonthlyOperationsCost() {
		return monthlyOperationsCost;
	}

	public void setMonthlyOperationsCost(Float monthlyOperationsCost) {
		this.monthlyOperationsCost = monthlyOperationsCost;
	}

	public Float getMonthlyValueLoss() {
		return monthlyValueLoss;
	}

	public void setMonthlyValueLoss(Float monthlyValueLoss) {
		this.monthlyValueLoss = monthlyValueLoss;
	}

	public Float getMonthlyTotalCost() {
		return monthlyTotalCost;
	}

	public void setMonthlyTotalCost(Float monthlyTotalCost) {
		this.monthlyTotalCost = monthlyTotalCost;
	}

	public Float getMonthlyFixCost() {
		return monthlyFixCost;
	}

	public void setMonthlyFixCost(Float monthlyFixCost) {
		this.monthlyFixCost = monthlyFixCost;
	}

	public Float getCostPerKilometer() {
		return costPerKilometer;
	}

	public void setCostPerKilometer(Float costPerKilometer) {
		this.costPerKilometer = costPerKilometer;
	}

}
