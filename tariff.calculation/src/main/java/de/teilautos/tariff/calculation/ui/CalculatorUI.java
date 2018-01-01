package de.teilautos.tariff.calculation.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private static final Logger logger = Logger.getLogger(CalculatorUI.class.getName());

	private static final Double KILOMETER_PRO_STUNDE = 7.5;
	private static final String INIT_KILOMETER_PER_YEAR = "1000";
	private static final Integer MAX_KILOMETER_PER_YEAR = 10000;

	private static final String WIDTH_PANE = "680px";
	private static final String WIDTH_SLIDER = "220";
	private static final String WIDTH_TEXT = "120px";
	private static final String WIDTH_SELECT = "320px";

	private ResourceBundle bundle = ResourceBundle.getBundle("calculator");

	private Slider kmSlider = new Slider(bundle.getString("kilometer_per_year"), 0, MAX_KILOMETER_PER_YEAR);
	private TextField kmText = new TextField(" ", Beautifier.kilometerUnit(INIT_KILOMETER_PER_YEAR));
	private NativeSelect<String> ownCarSelect = new NativeSelect<>(bundle.getString("my_car"));

	private Label costCarsharing = new Label(bundle.getString("cost_carsharing"));
	private Label costSavings = new Label(bundle.getString("cost_savings"));
	private Label costOwnCar = new Label(bundle.getString("cost_own_car"));

	private Label tariffRecommended = new Label();

	private Label valueLossCarsharing = new Label();
	private Label valueLossOwnCar = new Label();
	private Label fixCostCarsharing = new Label();
	private Label fixCostOwnCar = new Label();
	private Label garageCostCarsharing = new Label();
	private Label garageCostOwnCar = new Label();
	private Label operationsCostCarsharing = new Label();
	private Label operationsCostOwnCar = new Label();

	private Label valueLossSavings = new Label();
	private Label fixCostSavings = new Label();
	private Label garageCostSavings = new Label();
	private Label operationsCostSavings = new Label();

	private CalculationService calculationService = new CalculationService();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		logger.info("--> Initialising Calculator");
		final VerticalLayout root = new VerticalLayout();

		HorizontalLayout inputLayout = initInputPane();
		root.addComponent(inputLayout);

		root.addComponent(new HorizontalLayout());

		HorizontalLayout tariffLayout = initTariffPane();
		root.addComponent(tariffLayout);

		root.addComponent(new HorizontalLayout());

		GridLayout resultLayout = initCostPane();
		root.addComponent(resultLayout);

		root.addComponent(new HorizontalLayout());

		GridLayout detailsLayout = initDetailsPane();
		root.addComponent(detailsLayout);

		Label footer = new Label(bundle.getString("footer"));
		footer.setWidth(WIDTH_PANE);
		footer.setStyleName("footer");
		root.addComponent(footer);

		update();

		setSizeUndefined();
		setContent(root);

		logger.info("<-- Initialising Calculator");
	}

	private GridLayout initCostPane() {
		GridLayout costLayout = new GridLayout(3, 2);
		costLayout.setWidth(WIDTH_PANE);
		costLayout.setCaption(bundle.getString("mobility_cost"));

		// Header

		Label carsharingHeader = new Label(bundle.getString("cost_carsharing"));
		carsharingHeader.setStyleName("grid-header");
		costLayout.addComponent(carsharingHeader, 0, 0);
		costLayout.setComponentAlignment(carsharingHeader, Alignment.MIDDLE_CENTER);

		Label ownCarHeader = new Label(bundle.getString("cost_own_car"));
		ownCarHeader.setStyleName("grid-header");
		costLayout.addComponent(ownCarHeader, 1, 0);
		costLayout.setComponentAlignment(ownCarHeader, Alignment.MIDDLE_CENTER);

		Label savingsHeader = new Label(bundle.getString("cost_savings"));
		savingsHeader.setStyleName("grid-header");
		costLayout.addComponent(savingsHeader, 2, 0);
		costLayout.setComponentAlignment(savingsHeader, Alignment.MIDDLE_CENTER);

		// Panel

		costCarsharing.setStyleName("grid-cell");
		costLayout.addComponent(costCarsharing, 0, 1);
		costLayout.setComponentAlignment(costCarsharing, Alignment.MIDDLE_CENTER);

		costOwnCar.setStyleName("grid-cell");
		costLayout.addComponent(costOwnCar, 1, 1);
		costLayout.setComponentAlignment(costOwnCar, Alignment.MIDDLE_CENTER);

		costSavings.setStyleName("grid-cell");
		costLayout.addComponent(costSavings, 2, 1);
		costLayout.setComponentAlignment(costSavings, Alignment.MIDDLE_CENTER);

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

		kmText.setWidth(WIDTH_TEXT);
		kmText.setStyleName("kilometer");
		kmText.setEnabled(false);

		ownCarSelect.setWidth(WIDTH_SELECT);
		ownCarSelect.setItems(allCarTypeNames());
		ownCarSelect.setEmptySelectionAllowed(false);
		ownCarSelect.setSelectedItem(CarType.KLEINSTWAGEN.getName());
		ownCarSelect.addValueChangeListener(e -> this.onSliderChange());

		return new HorizontalLayout(kmSlider, kmText, ownCarSelect);
	}

	private GridLayout initDetailsPane() {
		GridLayout detailsLayout = new GridLayout(4, 5);
		detailsLayout.setWidth(WIDTH_PANE);
		detailsLayout.setCaption(bundle.getString("cost_details"));

		// Header

		Label costDriverHeader = new Label(bundle.getString("cost_driver"));
		costDriverHeader.setStyleName("grid-header");
		detailsLayout.addComponent(costDriverHeader, 0, 0);
		detailsLayout.setComponentAlignment(costDriverHeader, Alignment.MIDDLE_LEFT);

		Label carsharingHeader = new Label(bundle.getString("cost_carsharing"));
		carsharingHeader.setStyleName("grid-header");
		detailsLayout.addComponent(carsharingHeader, 1, 0);
		detailsLayout.setComponentAlignment(carsharingHeader, Alignment.MIDDLE_RIGHT);

		Label ownCarHeader = new Label(bundle.getString("cost_own_car"));
		ownCarHeader.setStyleName("grid-header");
		detailsLayout.addComponent(ownCarHeader, 2, 0);
		detailsLayout.setComponentAlignment(ownCarHeader, Alignment.MIDDLE_RIGHT);

		Label savingsHeader = new Label(bundle.getString("cost_savings"));
		savingsHeader.setStyleName("grid-header");
		detailsLayout.addComponent(savingsHeader, 3, 0);
		detailsLayout.setComponentAlignment(savingsHeader, Alignment.MIDDLE_RIGHT);

		// Column Cost Driver

		Label valueLossLabel = new Label(bundle.getString("cost_value_loss"));
		valueLossLabel.setStyleName("grid-cell");
		detailsLayout.addComponent(valueLossLabel, 0, 1);
		detailsLayout.setComponentAlignment(valueLossLabel, Alignment.MIDDLE_LEFT);

		Label fixCostLabel = new Label(bundle.getString("cost_fix"));
		fixCostLabel.setStyleName("grid-cell");
		detailsLayout.addComponent(fixCostLabel, 0, 2);
		detailsLayout.setComponentAlignment(fixCostLabel, Alignment.MIDDLE_LEFT);

		Label garageCostLabel = new Label(bundle.getString("cost_garage"));
		garageCostLabel.setStyleName("grid-cell");
		detailsLayout.addComponent(garageCostLabel, 0, 3);
		detailsLayout.setComponentAlignment(garageCostLabel, Alignment.MIDDLE_LEFT);

		Label operationsCostLabel = new Label(bundle.getString("cost_operation"));
		operationsCostLabel.setStyleName("grid-cell");
		detailsLayout.addComponent(operationsCostLabel, 0, 4);
		detailsLayout.setComponentAlignment(operationsCostLabel, Alignment.MIDDLE_LEFT);

		// Column Carsharing

		valueLossCarsharing.setStyleName("grid-cell");
		detailsLayout.addComponent(valueLossCarsharing, 1, 1);
		detailsLayout.setComponentAlignment(valueLossCarsharing, Alignment.MIDDLE_RIGHT);

		fixCostCarsharing.setStyleName("grid-cell");
		detailsLayout.addComponent(fixCostCarsharing, 1, 2);
		detailsLayout.setComponentAlignment(fixCostCarsharing, Alignment.MIDDLE_RIGHT);

		garageCostCarsharing.setStyleName("grid-cell");
		detailsLayout.addComponent(garageCostCarsharing, 1, 3);
		detailsLayout.setComponentAlignment(garageCostCarsharing, Alignment.MIDDLE_RIGHT);

		operationsCostCarsharing.setStyleName("grid-cell");
		detailsLayout.addComponent(operationsCostCarsharing, 1, 4);
		detailsLayout.setComponentAlignment(operationsCostCarsharing, Alignment.MIDDLE_RIGHT);

		// Column Own Car

		valueLossOwnCar.setStyleName("grid-cell");
		detailsLayout.addComponent(valueLossOwnCar, 2, 1);
		detailsLayout.setComponentAlignment(valueLossOwnCar, Alignment.MIDDLE_RIGHT);

		fixCostOwnCar.setStyleName("grid-cell");
		detailsLayout.addComponent(fixCostOwnCar, 2, 2);
		detailsLayout.setComponentAlignment(fixCostOwnCar, Alignment.MIDDLE_RIGHT);

		garageCostOwnCar.setStyleName("grid-cell");
		detailsLayout.addComponent(garageCostOwnCar, 2, 3);
		detailsLayout.setComponentAlignment(garageCostOwnCar, Alignment.MIDDLE_RIGHT);

		operationsCostOwnCar.setStyleName("grid-cell");
		detailsLayout.addComponent(operationsCostOwnCar, 2, 4);
		detailsLayout.setComponentAlignment(operationsCostOwnCar, Alignment.MIDDLE_RIGHT);

		// Column Savings

		valueLossSavings.setStyleName("grid-cell");
		detailsLayout.addComponent(valueLossSavings, 3, 1);
		detailsLayout.setComponentAlignment(valueLossSavings, Alignment.MIDDLE_RIGHT);

		fixCostSavings.setStyleName("grid-cell");
		detailsLayout.addComponent(fixCostSavings, 3, 2);
		detailsLayout.setComponentAlignment(fixCostSavings, Alignment.MIDDLE_RIGHT);

		garageCostSavings.setStyleName("grid-cell");
		detailsLayout.addComponent(garageCostSavings, 3, 3);
		detailsLayout.setComponentAlignment(garageCostSavings, Alignment.MIDDLE_RIGHT);

		operationsCostSavings.setStyleName("grid-cell");
		detailsLayout.addComponent(operationsCostSavings, 3, 4);
		detailsLayout.setComponentAlignment(operationsCostSavings, Alignment.MIDDLE_RIGHT);

		return detailsLayout;
	}

	private void update() {
		Double km = kmSlider.getValue();
		String tariffName = calculationService.getTariffName(km.intValue());
		Price price = calculationService.getPrice(tariffName);
		String carTypeName = ownCarSelect.getValue();

		if (logger.isLoggable(Level.INFO)) {
			StringBuffer buf = new StringBuffer();
			buf.append("km=").append(km).append(";");
			buf.append("carTypeName=").append(carTypeName).append(";");
			buf.append("tariffName=").append(tariffName).append(";");
			buf.append("price=").append(price.toString());
			logger.info(buf.toString());
		}

		double yearlyCostCarsharing = calculationService.yearlyCostCarsharing(km.intValue(), price,
				KILOMETER_PRO_STUNDE);
		costCarsharing.setValue(Beautifier.euros(yearlyCostCarsharing));

		double yearlyCostOwnCar = calculationService.yearlyTotalCostOwnCar(carTypeName, km.intValue());
		costOwnCar.setValue(Beautifier.euros(yearlyCostOwnCar));

		double yearlySavings = yearlyCostOwnCar - yearlyCostCarsharing;
		costSavings.setValue(Beautifier.euros(yearlySavings));

		tariffRecommended.setValue(tariffName);

		// Details pane

		DetailCarCost carCostOwnCar = calculationService.yearlyDetailCostOwnCar(ownCarSelect.getValue(), km.intValue());

		valueLossCarsharing.setValue(Beautifier.euros(0.0));
		valueLossOwnCar.setValue(Beautifier.euros(carCostOwnCar.getValueLoss()));
		valueLossSavings.setValue(Beautifier.euros(carCostOwnCar.getValueLoss() - 0.0));

		double yearlyContribution = calculationService.yearlyContribution(price.getMonthlyRate());
		fixCostCarsharing.setValue(Beautifier.euros(yearlyContribution));
		fixCostOwnCar.setValue(Beautifier.euros(carCostOwnCar.getFixCost()));
		fixCostSavings.setValue(Beautifier.euros(carCostOwnCar.getFixCost() - yearlyContribution));

		garageCostCarsharing.setValue(Beautifier.euros(0.0));
		garageCostOwnCar.setValue(Beautifier.euros(carCostOwnCar.getGarageCost()));
		garageCostSavings.setValue(Beautifier.euros(carCostOwnCar.getGarageCost() - 0.0));

		double hourlyRatesPerYear = calculationService.hourlyRatesPerYear(km.intValue(), KILOMETER_PRO_STUNDE,
				price.getHourlyRate());
		double kilometerPricesPerYear = calculationService.kilometerPricesPerYear(km.intValue(),
				price.getKilometerPrice());
		double operationsCostCarsharingValue = hourlyRatesPerYear + kilometerPricesPerYear;
		operationsCostCarsharing.setValue(Beautifier.euros(operationsCostCarsharingValue));
		operationsCostOwnCar.setValue(Beautifier.euros(carCostOwnCar.getOperationsCost()));
		operationsCostSavings
				.setValue(Beautifier.euros(carCostOwnCar.getOperationsCost() - operationsCostCarsharingValue));
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
