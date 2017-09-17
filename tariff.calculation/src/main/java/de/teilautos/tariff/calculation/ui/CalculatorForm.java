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
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.teilautos.tariff.calculation.domains.CarType;
import de.teilautos.tariff.calculation.domains.DetailCarCost;
import de.teilautos.tariff.calculation.domains.Price;
import de.teilautos.tariff.calculation.services.CalculationService;

public class CalculatorForm extends FormLayout {
	private static final Float KILOMETER_PRO_STUNDE = 7.5f;

	private static final long serialVersionUID = -7407551502394414191L;

	private Binder<CalculationModel> binder = new Binder<>(CalculationModel.class);

	private Label kmLabel = new Label("Gefahrene Kilometer pro Jahr");
	private TextField kilometer = new TextField();
	private Label kmUnitLabel = new Label(" km");
	private Slider kmSlider = new Slider(0, 15000);

	private TextField tariff = new TextField();
	private TextField costCarsharing = new TextField();
	private TextField costOwnCar = new TextField();
	private NativeSelect<String> ownCar = new NativeSelect<>("Mein Auto");

	private TextField valueLossCarsharing = new TextField();
	private TextField fixCostCarsharing = new TextField();
	private TextField garageCostCarsharing = new TextField();
	private TextField operationsCostCarsharing = new TextField();

	private Label valueLossLabel = new Label("Wertverlust");
	private Label fixCostLabel = new Label("Fixkosten");
	private Label garageCostLabel = new Label("Werkstatt");
	private Label operationsCostLabel = new Label("Fahrten");

	private TextField valueLossOwnCar = new TextField();
	private TextField fixCostOwnCar = new TextField();
	private TextField garageCostOwnCar = new TextField();
	private TextField operationsCostOwnCar = new TextField();

	private CalculationService calculationService = new CalculationService();
	private CalculationModel calculationModel = new CalculationModel();
	@SuppressWarnings("unused")
	private CalculatorUI ui;

	public CalculatorForm(CalculatorUI tariffCalculatorUI) {
		this.ui = tariffCalculatorUI;
		binder.bindInstanceFields(this);

		VerticalLayout inputPane = renderInputPane();
		updateInputPane();

		HorizontalLayout resultPane = new HorizontalLayout(tariff, costCarsharing, costOwnCar);
		updateResultPane();

		VerticalLayout detailPane = renderDetailPane();
		updateDetialPane();

		setSizeUndefined();
		addComponents(inputPane, resultPane, detailPane);
	}

	private VerticalLayout renderInputPane() {
		kilometer.setWidth("100px");
		kilometer.setEnabled(false);

		kmSlider.setWidth("500px");
		kmSlider.addValueChangeListener(e -> this.update());

		ownCar.setEmptySelectionAllowed(false);
		ownCar.setItems(allCarTypeNames());
		ownCar.setSelectedItem(CarType.KLEINSTWAGEN.getName());
		ownCar.addValueChangeListener(e -> this.update());

		HorizontalLayout kmPane = new HorizontalLayout(kilometer, kmUnitLabel, kmSlider, ownCar);
		VerticalLayout inputPane = new VerticalLayout(kmLabel, kmPane);
		return inputPane;
	}

	private VerticalLayout renderDetailPane() {
		HorizontalLayout valueLossPane = new HorizontalLayout(valueLossCarsharing, valueLossLabel, valueLossOwnCar);
		HorizontalLayout fixCostPane = new HorizontalLayout(fixCostCarsharing, fixCostLabel, fixCostOwnCar);
		HorizontalLayout garageCostPane = new HorizontalLayout(garageCostCarsharing, garageCostLabel, garageCostOwnCar);
		HorizontalLayout operationsCostPane = new HorizontalLayout(operationsCostCarsharing, operationsCostLabel,
				operationsCostOwnCar);

		VerticalLayout detailPane = new VerticalLayout(valueLossPane, fixCostPane, garageCostPane, operationsCostPane);
		return detailPane;
	}

	private void updateDetialPane() {
		Price price = calculationService.getPrice(calculationModel.getTariff());

		calculationModel.setValueLossCarsharing(beautifyCurrency("0.00"));
		valueLossCarsharing.setValue(calculationModel.getValueLossCarsharing());

		calculationModel.setFixCostCarsharing(
				beautifyCurrency("" + calculationService.yearlyContribution(price.getMonthlyRate())));
		fixCostCarsharing.setValue(calculationModel.getFixCostCarsharing());

		calculationModel.setGarageCostCarsharing(beautifyCurrency("0.00"));
		garageCostCarsharing.setValue(calculationModel.getGarageCostCarsharing());

		float hourlyRatesPerYear = calculationService.hourlyRatesPerYear(
				Integer.valueOf(calculationModel.getKilometer()), KILOMETER_PRO_STUNDE, price.getHourlyRate());
		float kilometerPricesPerYear = calculationService
				.kilometerPricesPerYear(Integer.valueOf(calculationModel.getKilometer()), price.getKilometerPrice());
		calculationModel.setOperationsCostCarsharing(beautifyCurrency("" + (hourlyRatesPerYear + kilometerPricesPerYear)));
		operationsCostCarsharing.setValue(calculationModel.getOperationsCostCarsharing());

		DetailCarCost detailCarCostOwnCar = calculationService.yearlyDetailCostOwnCar(ownCar.getValue(),
				Integer.valueOf(calculationModel.getKilometer()));

		calculationModel.setValueLossOwnCar(beautifyCurrency(detailCarCostOwnCar.getValueLossAsString()));
		valueLossOwnCar.setValue(calculationModel.getValueLossOwnCar());

		calculationModel.setFixCostOwnCar(beautifyCurrency(detailCarCostOwnCar.getFixCostAsString()));
		fixCostOwnCar.setValue(calculationModel.getFixCostOwnCar());

		calculationModel.setGarageCostOwnCar(beautifyCurrency(detailCarCostOwnCar.getGarageCostAsString()));
		garageCostOwnCar.setValue(calculationModel.getGarageCostOwnCar());

		calculationModel.setOperationsCostOwnCar(beautifyCurrency(detailCarCostOwnCar.getOperationsCostAsString()));
		operationsCostOwnCar.setValue(calculationModel.getOperationsCostOwnCar());
	}

	private void updateResultPane() {
		Integer kilometer = kmSlider.getValue().intValue();
		calculationModel.setKilometer("" + kilometer);

		String tariffName = calculationService.getTariffName(kilometer);
		calculationModel.setTariff(tariffName);

		tariff.setCaption("Tarifempfehlung");
		tariff.setValue(calculationModel.getTariff());
		tariff.setEnabled(false);

		// Carsharing Cost
		Price price = calculationService.getPrice(tariffName);
		float cost = calculationService.yearlyCostCarsharing(kilometer, price, KILOMETER_PRO_STUNDE);
		BigDecimal costCarsharingBd = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP);
		calculationModel.setCostCarsharing(costCarsharingBd.toPlainString());

		costCarsharing.setCaption("Carsharing");
		costCarsharing.setValue(beautifyCurrency(calculationModel.getCostCarsharing()));
		costCarsharing.setEnabled(false);

		// Own Car Cost
		float costOwnCarFloat = calculationService.yearlyTotalCostOwnCar(CarType.KLEINSTWAGEN.getName(), kilometer);
		BigDecimal costOwnCarBd = new BigDecimal(costOwnCarFloat).setScale(2, RoundingMode.HALF_UP);
		calculationModel.setCostOwnCar(costOwnCarBd.toPlainString());

		costOwnCar.setCaption("Eigenes Auto");
		costOwnCar.setValue(beautifyCurrency(calculationModel.getCostOwnCar()));
		costOwnCar.setEnabled(false);
	}

	private List<String> allCarTypeNames() {
		List<String> names = new ArrayList<>();
		for (CarType carType : CarType.values()) {
			String name = carType.getName();
			names.add(name);
		}
		return names;
	}

	private void updateInputPane() {
		kilometer.setValue(String.valueOf(calculationModel.getKilometer()));

		kmSlider.setValue(Double.valueOf(calculationModel.getKilometer()));
	}

	private void setCalculation(CalculationModel calculation) {
		this.calculationModel = calculation;
		binder.setBean(calculation);
	}

	private void update() {
		updateResultPane();
		updateDetialPane();

		setCalculation(calculationModel);
	}

	@SuppressWarnings("unused")
	private String beautifyKilometer(String km) {
		String result = km + " km";
		return result;
	}

	private String beautifyCurrency(String amount) {
		String result = amount.substring(0, amount.indexOf("."));
		result += " €";
		return result;
	}
}
