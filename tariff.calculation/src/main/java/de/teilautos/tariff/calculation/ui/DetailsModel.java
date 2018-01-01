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

public class DetailsModel {

	private double valueLossCarsharing;
	private double fixCostCarsharing;
	private double garageCostCarsharing;
	private double operationCostCarsharing;

	private double valueLossOwnCar;
	private double fixCostOwnCar;
	private double garageCostOwnCar;
	private double operationCostOwnCar;

	// private double valueLossSavings;
	// private double fixCostSavings;
	// private double garageCostSavings;
	// private double operationCostSavings;

	public DetailsModel() {
	}

	public String getValueLossSavings(String costOwnCar, String costCarsharing) {
		double savings = Double.valueOf(costOwnCar) - Double.valueOf(costCarsharing);
		String result = new BigDecimal(savings).setScale(2, RoundingMode.HALF_UP).toPlainString();
		return result;
	}

	public String getFixCostSavings(String costOwnCar, String costCarsharing) {
		double savings = Double.valueOf(costOwnCar) - Double.valueOf(costCarsharing);
		String result = new BigDecimal(savings).setScale(2, RoundingMode.HALF_UP).toPlainString();
		return result;
	}

	public String getGarageCostSavings(String costOwnCar, String costCarsharing) {
		double savings = Double.valueOf(costOwnCar) - Double.valueOf(costCarsharing);
		String result = new BigDecimal(savings).setScale(2, RoundingMode.HALF_UP).toPlainString();
		return result;
	}

	public String getOperationCostSavings(String costOwnCar, String costCarsharing) {
		double savings = Double.valueOf(costOwnCar) - Double.valueOf(costCarsharing);
		String result = new BigDecimal(savings).setScale(2, RoundingMode.HALF_UP).toPlainString();
		return result;
	}

	public double getValueLossCarsharing() {
		return valueLossCarsharing;
	}

	public void setValueLossCarsharing(double valueLossCarsharing) {
		this.valueLossCarsharing = valueLossCarsharing;
	}

	public double getFixCostCarsharing() {
		return fixCostCarsharing;
	}

	public void setFixCostCarsharing(double fixCostCarsharing) {
		this.fixCostCarsharing = fixCostCarsharing;
	}

	public double getGarageCostCarsharing() {
		return garageCostCarsharing;
	}

	public void setGarageCostCarsharing(double garageCostCarsharing) {
		this.garageCostCarsharing = garageCostCarsharing;
	}

	public double getOperationCostCarsharing() {
		return operationCostCarsharing;
	}

	public void setOperationCostCarsharing(double operationCostCarsharing) {
		this.operationCostCarsharing = operationCostCarsharing;
	}

	public double getValueLossOwnCar() {
		return valueLossOwnCar;
	}

	public void setValueLossOwnCar(double valueLossOwnCar) {
		this.valueLossOwnCar = valueLossOwnCar;
	}

	public double getFixCostOwnCar() {
		return fixCostOwnCar;
	}

	public void setFixCostOwnCar(double fixCostOwnCar) {
		this.fixCostOwnCar = fixCostOwnCar;
	}

	public double getGarageCostOwnCar() {
		return garageCostOwnCar;
	}

	public void setGarageCostOwnCar(double garageCostOwnCar) {
		this.garageCostOwnCar = garageCostOwnCar;
	}

	public double getOperationCostOwnCar() {
		return operationCostOwnCar;
	}

	public void setOperationCostOwnCar(double operationCostOwnCar) {
		this.operationCostOwnCar = operationCostOwnCar;
	}

}
