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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import de.teilautos.tariff.calculation.domains.CarType;
import de.teilautos.tariff.calculation.domains.DetailCarCost;
import de.teilautos.tariff.calculation.domains.Price;
import de.teilautos.tariff.calculation.services.CalculationService;

public class CalculatorForm extends FormLayout {
	private static final long serialVersionUID = -7407551502394414191L;

	@SuppressWarnings("unused")
	private CalculatorUI ui;

	private static final Double KILOMETER_PRO_STUNDE = 7.5;
	private static final String INIT_KILOMETER_PER_YEAR = "1000";

	private ResourceBundle bundle = ResourceBundle.getBundle("calculator");

	private Label kmLabel = new Label();
	private Slider kmSlider = new Slider(0, 15000);
	private Label ownCarLabel = new Label();
	private NativeSelect<String> ownCarSelect = new NativeSelect<>();

	private Label costLabel = new Label();
	private Label costCarsharingLabel = new Label();
	private Label costSavingsLabel = new Label();
	private Label costOwnCarLabel = new Label();
	private Label costCarsharingValue = new Label();
	private Label costSavingsValue = new Label();
	private Label costOwnCarValue = new Label();
	// TODO OHO Image to visualize cost and savings.

	private Label tariffRecommendedLabel = new Label();
	private Label tariffRecommendedValue = new Label();

	private Label detailsLabel = new Label();
	private Label valueLossLabel = new Label();
	private Label fixCostLabel = new Label();
	private Label garageCostLabel = new Label();
	private Label operationsCostLabel = new Label();

	private Label valueLossCarsharingValue = new Label();
	private Label valueLossOwnCarValue = new Label();
	private Label fixCostCarsharingValue = new Label();
	private Label fixCostOwnCarValue = new Label();
	private Label garageCostCarsharingValue = new Label();
	private Label garageCostOwnCarValue = new Label();
	private Label operationsCostCarsharingValue = new Label();
	private Label operationsCostOwnCarValue = new Label();

	private CalculationService calculationService = new CalculationService();

	public CalculatorForm(CalculatorUI tariffCalculatorUI) {
		this.ui = tariffCalculatorUI;

		VerticalLayout inputForm = initInputPane();
		VerticalLayout resultForm = initResultPane();
		VerticalLayout detailsForm = initDetailsPane();

		update();

		setSizeUndefined();
		addComponents(inputForm, resultForm, detailsForm);
	}

	private VerticalLayout initInputPane() {
		kmLabel.setValue(MessageFormat.format(bundle.getString("kilometer_per_year"), INIT_KILOMETER_PER_YEAR));

		kmSlider.setWidth("500px");
		kmSlider.setValue(Double.valueOf(INIT_KILOMETER_PER_YEAR));
		kmSlider.addValueChangeListener(e -> this.onChange());

		ownCarLabel.setValue(bundle.getString("my_car"));
		ownCarSelect.setItems(allCarTypeNames());
		ownCarSelect.setEmptySelectionAllowed(false);
		ownCarSelect.setSelectedItem(CarType.KLEINSTWAGEN.getName());
		ownCarSelect.addValueChangeListener(e -> this.onChange());

		HorizontalLayout ownCarPane = new HorizontalLayout(ownCarLabel, ownCarSelect);

		VerticalLayout inputPane = new VerticalLayout(kmSlider, kmLabel, ownCarPane);
		return inputPane;
	}

	private VerticalLayout initResultPane() {
		costLabel.setValue(bundle.getString("mobility_cost"));

		costCarsharingLabel.setValue(bundle.getString("cost_carsharing"));
		costSavingsLabel.setValue(bundle.getString("cost_savings"));
		costOwnCarLabel.setValue(bundle.getString("cost_own_car"));
		HorizontalLayout labelPane = new HorizontalLayout(costCarsharingLabel, costSavingsLabel, costOwnCarLabel);

		HorizontalLayout valuePane = new HorizontalLayout(costCarsharingValue, costSavingsValue, costOwnCarValue);

		// TODO OHO Add visual image to show savings here.

		tariffRecommendedLabel.setValue(bundle.getString("tariff_recommended"));
		HorizontalLayout tariffPane = new HorizontalLayout(tariffRecommendedLabel, tariffRecommendedValue);

		VerticalLayout resultPane = new VerticalLayout(costLabel, labelPane, valuePane, tariffPane);
		return resultPane;
	}

	private VerticalLayout initDetailsPane() {
		detailsLabel.setValue(bundle.getString("cost_details"));

		fixCostLabel.setValue(bundle.getString("cost_fix"));
		garageCostLabel.setValue(bundle.getString("cost_garage"));
		operationsCostLabel.setValue(bundle.getString("cost_operation"));

		valueLossLabel.setValue(bundle.getString("cost_value_loss"));
		HorizontalLayout valueLossPane = new HorizontalLayout(valueLossCarsharingValue, valueLossLabel,
				valueLossOwnCarValue);
		HorizontalLayout fixCostPane = new HorizontalLayout(fixCostCarsharingValue, fixCostLabel, fixCostOwnCarValue);
		HorizontalLayout garageCostPane = new HorizontalLayout(garageCostCarsharingValue, garageCostLabel,
				garageCostOwnCarValue);
		HorizontalLayout operationsCostPane = new HorizontalLayout(operationsCostCarsharingValue, operationsCostLabel,
				operationsCostOwnCarValue);
		VerticalLayout pane = new VerticalLayout(detailsLabel, valueLossPane, fixCostPane, garageCostPane,
				operationsCostPane);

		return pane;
	}

	private void update() {
		Double km = kmSlider.getValue();
		String tariffName = calculationService.getTariffName(km.intValue());
		Price price = calculationService.getPrice(tariffName);
		String carTypeName = ownCarSelect.getValue();

		double yearlyCostCarsharing = calculationService.yearlyCostCarsharing(km.intValue(), price,
				KILOMETER_PRO_STUNDE);
		costCarsharingValue.setValue(Beautifier.euros(yearlyCostCarsharing));

		double yearlyCostOwnCar = calculationService.yearlyTotalCostOwnCar(carTypeName, km.intValue());
		costOwnCarValue.setValue(Beautifier.euros(yearlyCostOwnCar));

		double yearlySavings = yearlyCostOwnCar - yearlyCostCarsharing;
		costSavingsValue.setValue(Beautifier.euros(yearlySavings));

		tariffRecommendedValue.setValue(tariffName);

		DetailCarCost carCostOwnCar = calculationService.yearlyDetailCostOwnCar(ownCarSelect.getValue(), km.intValue());

		valueLossCarsharingValue.setValue(Beautifier.euros(0.0));
		valueLossOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getValueLoss()));
		
		double yearlyContribution = calculationService.yearlyContribution(price.getMonthlyRate());
		fixCostCarsharingValue.setValue(Beautifier.euros(yearlyContribution));
		fixCostOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getFixCost()));
		
		garageCostCarsharingValue.setValue(Beautifier.euros(0.0));
		garageCostOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getGarageCost()));
		
		double hourlyRatesPerYear = calculationService.hourlyRatesPerYear(km.intValue(), KILOMETER_PRO_STUNDE, price.getHourlyRate());
		double kilometerPricesPerYear = calculationService
				.kilometerPricesPerYear(km.intValue(), price.getKilometerPrice());
		double operationsCostCarsharing = hourlyRatesPerYear + kilometerPricesPerYear;
		operationsCostCarsharingValue.setValue(Beautifier.euros(operationsCostCarsharing));
		operationsCostOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getOperationsCost()));
	}

	private List<String> allCarTypeNames() {
		List<String> names = new ArrayList<>();
		for (CarType carType : CarType.values()) {
			String name = carType.getName();
			names.add(name);
		}
		return names;
	}

	private void onChange() {
		Double kmValue = kmSlider.getValue();
		String kmStr = Beautifier.kilometer(kmValue);
		String label = MessageFormat.format(bundle.getString("kilometer_per_year"), kmStr);
		kmLabel.setValue(label);

		update();
	}

}
