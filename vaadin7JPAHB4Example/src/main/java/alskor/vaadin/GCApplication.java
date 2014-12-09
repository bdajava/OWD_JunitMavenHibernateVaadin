package alskor.vaadin;

import alskor.vaadin.db.TripManager;
import alskor.vaadin.page.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.servlet.annotation.WebServlet;

/**
 * This is the Vaadin web application entry point.
 * 
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.mycompany.vaadintest1.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

 * 
 */
@Theme("mytheme")
public class GCApplication extends UI {
    private Navigator navigator;
    private Authenticator authenticator;
    private TripManager manager = new TripManager();

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = GCApplication.class, widgetset = "alskor.vaadin.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }
    
    
    
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        navigator = new Navigator(this, this);
        authenticator = new Authenticator();
        // Create and register the views
        navigator.addView("", new MainView(navigator, authenticator, manager));

        SampleDataLoader.loadSampleData(manager);
    }
}
