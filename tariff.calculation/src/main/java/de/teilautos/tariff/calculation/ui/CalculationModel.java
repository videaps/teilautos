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

import de.teilautos.tariff.calculation.domains.CarType;

public class CalculationModel {

	private String kilometer = "0";
	private String ownCarName = CarType.KLEINSTWAGEN.getName();
	private String tariff;
	private String costCarsharing;
	private String costOwnCar;

	private String valueLossCarsharing;
	private String valueLossOwnCar;
	private String fixCostCarsharing;
	private String fixCostOwnCar;
	private String garageCostCarsharing;
	private String garageCostOwnCar;
	private String operationsCostCarsharing;
	private String operationsCostOwnCar;
	
	public String getValueLossCarsharing() {
		return valueLossCarsharing;
	}

	public void setValueLossCarsharing(String valueLossCarsharing) {
		this.valueLossCarsharing = valueLossCarsharing;
	}

	public String getValueLossOwnCar() {
		return valueLossOwnCar;
	}

	public void setValueLossOwnCar(String valueLossOwnCar) {
		this.valueLossOwnCar = valueLossOwnCar;
	}

	public String getFixCostCarsharing() {
		return fixCostCarsharing;
	}

	public void setFixCostCarsharing(String fixCostCarsharing) {
		this.fixCostCarsharing = fixCostCarsharing;
	}

	public String getFixCostOwnCar() {
		return fixCostOwnCar;
	}

	public void setFixCostOwnCar(String fixCostOwnCar) {
		this.fixCostOwnCar = fixCostOwnCar;
	}

	public String getGarageCostCarsharing() {
		return garageCostCarsharing;
	}

	public void setGarageCostCarsharing(String garageCostCarsharing) {
		this.garageCostCarsharing = garageCostCarsharing;
	}

	public String getGarageCostOwnCar() {
		return garageCostOwnCar;
	}

	public void setGarageCostOwnCar(String garageCostOwnCar) {
		this.garageCostOwnCar = garageCostOwnCar;
	}

	public String getOperationsCostCarsharing() {
		return operationsCostCarsharing;
	}

	public void setOperationsCostCarsharing(String operationsCostCarsharing) {
		this.operationsCostCarsharing = operationsCostCarsharing;
	}

	public String getOperationsCostOwnCar() {
		return operationsCostOwnCar;
	}

	public void setOperationsCostOwnCar(String operationsCostOwnCar) {
		this.operationsCostOwnCar = operationsCostOwnCar;
	}

	public String getKilometer() {
		return kilometer;
	}

	public void setKilometer(String kilometer) {
		this.kilometer = kilometer;
	}

	public String getOwnCarName() {
		return ownCarName;
	}

	public void setOwnCarName(String ownCarName) {
		this.ownCarName = ownCarName;
	}

	public String getTariff() {
		return tariff;
	}

	public void setTariff(String tariff) {
		this.tariff = tariff;
	}

	public String getCostCarsharing() {
		return costCarsharing;
	}

	public void setCostCarsharing(String costCarsharing) {
		this.costCarsharing = costCarsharing;
	}

	public String getCostOwnCar() {
		return costOwnCar;
	}

	public void setCostOwnCar(String costOwnCar) {
		this.costOwnCar = costOwnCar;
	}

}
