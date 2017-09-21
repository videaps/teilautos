package de.teilautos.tariff.calculation.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("teilautos")
public class CalculatorUI extends UI {
	private static final long serialVersionUID = -3570006873635093879L;

	private CalculatorForm form = new CalculatorForm(this);
	
	@Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        HorizontalLayout pane = new HorizontalLayout(form);
        pane.setSizeFull();
       
        layout.addComponents(pane);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "CalculatorUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CalculatorUI.class, productionMode = false)
    public static class TariffCalculatorUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 582364703069431124L;
    }
    
}
