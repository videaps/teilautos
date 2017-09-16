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

import com.vaadin.data.Binder;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.teilautos.tariff.calculation.rules.Price;
import de.teilautos.tariff.calculation.services.CalculationService;

public class CalculatorForm extends FormLayout {
	private static final long serialVersionUID = -7407551502394414191L;

	private Binder<CalculationModel> binder = new Binder<>(CalculationModel.class);

	private Label kmLabel = new Label("Meine gefahrenen Kilometer pro Jahr");
	private TextField kilometer = new TextField();
	private Slider kmSlider = new Slider(0, 10000);

	private TextField tariff = new TextField();
	private TextField costOccupant = new TextField();

	private CalculationService calculationService = new CalculationService();
	private CalculationModel calculationModel = new CalculationModel();
	@SuppressWarnings("unused")
	private CalculatorUI ui;

	public CalculatorForm(CalculatorUI tariffCalculatorUI) {
		this.ui = tariffCalculatorUI;
		binder.bindInstanceFields(this);

		VerticalLayout inputPane = renderInputPane();
		HorizontalLayout resultPane = renderResultPane();

		setSizeUndefined();
		addComponents(inputPane, resultPane);
	}

	private HorizontalLayout renderResultPane() {
		String kmYear = calculationModel.getKilometer();
		calculationModel.setKilometer(kmYear);
		String tariffName = calculationService.getTariffName(Integer.valueOf(kmYear));
		calculationModel.setTariff(tariffName);

		tariff.setValue(calculationModel.getTariff());
		tariff.setEnabled(false);

		Price price = calculationService.getPrice(tariffName);
		float cost = calculationService.yearlyCostOccupant(kmYear, price, "7.5");
		BigDecimal costOccupantBd = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
		calculationModel.setCostOccupant(costOccupantBd.toPlainString());
		
		costOccupant.setValue(calculationModel.getCostOccupant());
		costOccupant.setEnabled(false);
		
		HorizontalLayout costPane = new HorizontalLayout(tariff, costOccupant);
		
		return costPane;
	}

	private VerticalLayout renderInputPane() {
		kilometer.setValue(calculationModel.getKilometer());

		kmSlider.setValue(Double.valueOf(calculationModel.getKilometer()));
		kmSlider.addValueChangeListener(e -> this.slide());

		HorizontalLayout kmPane = new HorizontalLayout(kilometer, kmSlider);
		VerticalLayout inputPane = new VerticalLayout(kmLabel, kmPane);
		return inputPane;
	}

	public void setCalculation(CalculationModel calculation) {
		this.calculationModel = calculation;
		binder.setBean(calculation);
	}

	private void slide() {
		int kilometer = kmSlider.getValue().intValue();
		calculationModel.setKilometer("" + kilometer);

		String tariffName = calculationService.getTariffName(kilometer);
		calculationModel.setTariff(tariffName);

		Price price = calculationService.getPrice(tariffName);
		float costOccupant = calculationService.yearlyCostOccupant(calculationModel.getKilometer(), price, "7.5");
		BigDecimal costOccupantBd = new BigDecimal(costOccupant).setScale(2, RoundingMode.HALF_UP);
		calculationModel.setCostOccupant(costOccupantBd.toPlainString());
		
		setCalculation(calculationModel);
	}
}
