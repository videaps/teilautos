/*
 Copyright (c) 2018 Videa Project Services GmbH

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
package de.teilautos.fahrtkostenrechner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("teilautos")
public class ApplicationUI extends UI {
	private static final long serialVersionUID = 1438681489913367197L;
	private static final Logger logger = Logger.getLogger(ApplicationUI.class.getName());

	private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");

	private TariffService tariffService = new TariffService();
	private TariffModel tariffModel = null;

	private Label title = new Label(bundle.getString("title"));
	
	private NativeSelect<String> tariffSelect = new NativeSelect<>(bundle.getString("tarif"));

	private Label plannedJourneyLabel = new Label(bundle.getString("geplante_fahrt"));

	private TextField distanceField = new TextField(bundle.getString("kilometer"));

	private Integer distanceLastValue;
	private Integer distanceCurrentValue;

	private TextField durationField = new TextField(bundle.getString("stunden"));

	private Integer durationLastValue;
	private Integer durationCurrentValue;

	private TextField journeyCostField = new TextField(
			bundle.getString("fahrtkosten") + " (" + bundle.getString("euro") + ")");

	static {
		String path = ApplicationUI.class.getClassLoader().getResource("logging.properties").getPath();
		logger.info("path=" + path);
		System.setProperty("java.util.logging.config.file", path);

		Locale.setDefault(new Locale("de", "DE"));
	}

	@Override
	protected void init(VaadinRequest request) {
		logger.entering(ApplicationUI.class.getSimpleName(), "init");

		final VerticalLayout root = new VerticalLayout();

		root.addComponent(new HorizontalLayout(title));
		
		Collection<String> items = new ArrayList<>();
		for (TariffEnum tariff : TariffEnum.values()) {
			items.add(tariff.getName());
		}
		tariffSelect.setItems(items);
		tariffSelect.setWidth("320px");
		tariffSelect.setEmptySelectionAllowed(false);
		tariffSelect.setSelectedItem(TariffEnum.NORMAL.getName());
		tariffSelect.addValueChangeListener(e -> this.onTariffChange());
		tariffSelect.setWidth("215px");

		HorizontalLayout tariffLayout = new HorizontalLayout(tariffSelect);
		root.addComponent(tariffLayout);

		root.addComponent(new HorizontalLayout());
		root.addComponent(new HorizontalLayout(plannedJourneyLabel));

		distanceField.setValue("20");
		distanceField.addValueChangeListener(listener -> this.onDistanceChange());
		distanceField.setStyleName("kilometer");
		distanceField.setWidth("100px");
		distanceCurrentValue = new Integer(distanceField.getValue());
		distanceLastValue = distanceCurrentValue;

		durationField.setValue("3");
		durationField.addValueChangeListener(listener -> this.onDurationChange());
		durationField.setStyleName("kilometer");
		durationField.setWidth("100px");
		durationCurrentValue = new Integer(durationField.getValue());
		durationLastValue = durationCurrentValue;

		root.addComponent(new HorizontalLayout(distanceField, durationField));

		root.addComponent(new HorizontalLayout());

		journeyCostField.setEnabled(false);
		journeyCostField.setStyleName("kilometer");
		journeyCostField.setWidth("215px");
		
		root.addComponent(new HorizontalLayout(journeyCostField));

		setSizeUndefined();
		setContent(root);

		onTariffChange();

		logger.exiting(ApplicationUI.class.getSimpleName(), "init");
	}

	private void onTariffChange() {
		String tariffName = tariffSelect.getValue();
		this.tariffModel = tariffService.getTariff(tariffName);

		this.calculate();
	}

	private void onDistanceChange() {
		try {
			distanceCurrentValue = Integer.valueOf(distanceField.getValue());
		} catch (NumberFormatException e) {
			distanceCurrentValue = distanceLastValue;
		}
		distanceField.setValue(distanceCurrentValue.toString());

		this.calculate();
	}

	private void onDurationChange() {
		try {
			durationCurrentValue = Integer.valueOf(durationField.getValue());
		} catch (NumberFormatException e) {
			durationCurrentValue = durationLastValue;
		}
		durationField.setValue(durationCurrentValue.toString());

		this.calculate();
	}

	private void calculate() {
		int distance = distanceCurrentValue.intValue();
		int duration = durationCurrentValue.intValue();

		double distanceCost = distance * tariffModel.getKilometerPrice();
		double durationCost = duration * tariffModel.getHourlyPrice();

		double totalCost = distanceCost + durationCost;
		journeyCostField.setValue(euros(totalCost));
	}

	public static String euros(double amount) {
		BigDecimal bd = new BigDecimal(amount).setScale(2, RoundingMode.HALF_DOWN);
		String result = bd.toPlainString();
		result = result.replace('.', ',');
		return result;
	}

	@WebServlet(urlPatterns = "/*", name = "ApplicationUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = ApplicationUI.class, productionMode = true)
	public static class ApplicationUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 8619357658744858259L;
	}

}
