package de.teilautos.tariff.calculation.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.teilautos.tariff.calculation.domains.CarType;
import de.teilautos.tariff.calculation.domains.DetailCarCost;
import de.teilautos.tariff.calculation.domains.Price;
import de.teilautos.tariff.calculation.services.CalculationService;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("teilautos")
public class CalculatorUI extends UI {
	private static final long serialVersionUID = -3570006873635093879L;

	private static final Double KILOMETER_PRO_STUNDE = 7.5;
	private static final String INIT_KILOMETER_PER_YEAR = "1000";
	private static final Integer MAX_KILOMETER_PER_YEAR = 10000;

	private static final String WIDTH_PANE = "680px";
	private static final String WIDTH_SLIDER = "220";
	private static final String WIDTH_TEXT_FIELD = "120px";
	private static final String WIDTH_SELECT = "320px";
	private static final String WIDTH_COST_FIELD = "220px";
	
	private ResourceBundle bundle = ResourceBundle.getBundle("calculator");

	private Slider kmSlider = new Slider(bundle.getString("kilometer_per_year"), 0, MAX_KILOMETER_PER_YEAR);
	private TextField kmText = new TextField(" ", Beautifier.kilometerUnit(INIT_KILOMETER_PER_YEAR));
	private NativeSelect<String> ownCarSelect = new NativeSelect<>(bundle.getString("my_car"));

	private Label costCarsharing = new Label(bundle.getString("cost_carsharing"));
	private Label costSavings = new Label(bundle.getString("cost_savings"));
	private Label costOwnCar = new Label(bundle.getString("cost_own_car"));

	private Label tariffRecommended = new Label();

	private Label valueLossLabel = new Label(bundle.getString("cost_value_loss"));
	private Label fixCostLabel = new Label(bundle.getString("cost_fix"));
	private Label garageCostLabel = new Label(bundle.getString("cost_garage"));
	private Label operationsCostLabel = new Label(bundle.getString("cost_operation"));

	private TextField valueLossCarsharingValue = new TextField();
	private TextField valueLossOwnCarValue = new TextField();
	private TextField fixCostCarsharingValue = new TextField();
	private TextField fixCostOwnCarValue = new TextField();
	private TextField garageCostCarsharingValue = new TextField();
	private TextField garageCostOwnCarValue = new TextField();
	private TextField operationsCostCarsharingValue = new TextField();
	private TextField operationsCostOwnCarValue = new TextField();

	private CalculationService calculationService = new CalculationService();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout root = new VerticalLayout();

		HorizontalLayout inputLayout = initInputPane();
		root.addComponent(inputLayout);

		root.addComponent(new HorizontalLayout());

		HorizontalLayout tariffLayout = initTariffPane();
		root.addComponent(tariffLayout);

		root.addComponent(new HorizontalLayout());

		GridLayout resultLayout = initCostPane();
		root.addComponent(resultLayout);

		VerticalLayout detailsLayout = initDetailsPane();
		root.addComponent(detailsLayout);

		update();

		setSizeUndefined();
		setContent(root);
	}

	private GridLayout initCostPane() {
		GridLayout costLayout = new GridLayout(3, 2);
		costLayout.setWidth(WIDTH_PANE);
		costLayout.setCaption(bundle.getString("mobility_cost"));
		
		Label carsharingHeader = new Label(bundle.getString("cost_carsharing"));
		carsharingHeader.setStyleName("grid-header");
		costLayout.addComponent(carsharingHeader, 0, 0);
		costLayout.setComponentAlignment(carsharingHeader, Alignment.MIDDLE_CENTER);
		
		Label savingsHeader = new Label(bundle.getString("cost_savings"));
		savingsHeader.setStyleName("grid-header");
		costLayout.addComponent(savingsHeader, 1, 0);
		costLayout.setComponentAlignment(savingsHeader, Alignment.MIDDLE_CENTER);

		Label ownCarHeader = new Label(bundle.getString("cost_own_car"));
		ownCarHeader.setStyleName("grid-header");
		costLayout.addComponent(ownCarHeader, 2, 0);
		costLayout.setComponentAlignment(ownCarHeader, Alignment.MIDDLE_CENTER);

		costCarsharing.setStyleName("grid-cell");
		costLayout.addComponent(costCarsharing, 0, 1);
		costLayout.setComponentAlignment(costCarsharing, Alignment.MIDDLE_CENTER);
		
		costSavings.setStyleName("grid-cell");
		costLayout.addComponent(costSavings, 1, 1);
		costLayout.setComponentAlignment(costSavings, Alignment.MIDDLE_CENTER);

		costOwnCar.setStyleName("grid-cell");
		costLayout.addComponent(costOwnCar, 2, 1);
		costLayout.setComponentAlignment(costOwnCar, Alignment.MIDDLE_CENTER);
		
		return costLayout;
	}

	private HorizontalLayout initTariffPane() {
		Label tariffLabel = new Label(bundle.getString("tariff_recommended") + ":");
		tariffLabel.setStyleName("tariff");

		tariffRecommended.setStyleName("recommendation");
		
		HorizontalLayout tariffLayout = new HorizontalLayout(tariffLabel, tariffRecommended);
		return tariffLayout;
	}

	@WebServlet(urlPatterns = "/*", name = "CalculatorUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = CalculatorUI.class, productionMode = false)
	public static class TariffCalculatorUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 582364703069431124L;
	}

	private HorizontalLayout initInputPane() {
		kmSlider.setWidth(WIDTH_SLIDER);
		kmSlider.setValue(Double.valueOf(INIT_KILOMETER_PER_YEAR));
		kmSlider.addValueChangeListener(e -> this.onSliderChange());

		kmText.setWidth(WIDTH_TEXT_FIELD);
		kmText.setStyleName("kilometer");
		kmText.setEnabled(false);

		ownCarSelect.setWidth(WIDTH_SELECT);
		ownCarSelect.setItems(allCarTypeNames());
		ownCarSelect.setEmptySelectionAllowed(false);
		ownCarSelect.setSelectedItem(CarType.KLEINSTWAGEN.getName());
		ownCarSelect.addValueChangeListener(e -> this.onSliderChange());

		return new HorizontalLayout(kmSlider, kmText, ownCarSelect);
	}

	private VerticalLayout initDetailsPane() {
		HorizontalLayout valueLossPane = new HorizontalLayout(valueLossCarsharingValue, valueLossLabel,
				valueLossOwnCarValue);
		HorizontalLayout fixCostPane = new HorizontalLayout(fixCostCarsharingValue, fixCostLabel, fixCostOwnCarValue);
		HorizontalLayout garageCostPane = new HorizontalLayout(garageCostCarsharingValue, garageCostLabel,
				garageCostOwnCarValue);
		HorizontalLayout operationsCostPane = new HorizontalLayout(operationsCostCarsharingValue, operationsCostLabel,
				operationsCostOwnCarValue);

		return new VerticalLayout(valueLossPane, fixCostPane, garageCostPane, operationsCostPane);
	}

	private void update() {
		Double km = kmSlider.getValue();
		String tariffName = calculationService.getTariffName(km.intValue());
		Price price = calculationService.getPrice(tariffName);
		String carTypeName = ownCarSelect.getValue();

		double yearlyCostCarsharing = calculationService.yearlyCostCarsharing(km.intValue(), price,
				KILOMETER_PRO_STUNDE);
		costCarsharing.setValue(Beautifier.euros(yearlyCostCarsharing));

		double yearlyCostOwnCar = calculationService.yearlyTotalCostOwnCar(carTypeName, km.intValue());
		costOwnCar.setValue(Beautifier.euros(yearlyCostOwnCar));

		double yearlySavings = yearlyCostOwnCar - yearlyCostCarsharing;
		costSavings.setValue(Beautifier.euros(yearlySavings));

		tariffRecommended.setValue(tariffName);

		DetailCarCost carCostOwnCar = calculationService.yearlyDetailCostOwnCar(ownCarSelect.getValue(), km.intValue());

		valueLossCarsharingValue.setValue(Beautifier.euros(0.0));
		valueLossOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getValueLoss()));

		double yearlyContribution = calculationService.yearlyContribution(price.getMonthlyRate());
		fixCostCarsharingValue.setValue(Beautifier.euros(yearlyContribution));
		fixCostOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getFixCost()));

		garageCostCarsharingValue.setValue(Beautifier.euros(0.0));
		garageCostOwnCarValue.setValue(Beautifier.euros(carCostOwnCar.getGarageCost()));

		double hourlyRatesPerYear = calculationService.hourlyRatesPerYear(km.intValue(), KILOMETER_PRO_STUNDE,
				price.getHourlyRate());
		double kilometerPricesPerYear = calculationService.kilometerPricesPerYear(km.intValue(),
				price.getKilometerPrice());
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

	@SuppressWarnings("unused")
	private void onKilometerChange() {
		String kmStr = kmText.getValue();
		Double kmValue = Double.valueOf(kmStr);
		kmSlider.setValue(kmValue);
		
		update();
	}
	
	private void onSliderChange() {
		Double kmValue = kmSlider.getValue();
		String kmStr = Beautifier.kilometer(kmValue);
		kmText.setValue(Beautifier.kilometerUnit(kmStr));

		update();
	}

}
