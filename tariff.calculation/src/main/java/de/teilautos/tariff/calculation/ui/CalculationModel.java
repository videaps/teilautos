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
