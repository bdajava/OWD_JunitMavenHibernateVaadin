package com.mycompany.vaadinviews;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tests.minitutorials.v7b6.OpeningUIInPopup;
import com.vaadin.tests.minitutorials.v7b9.CountView;
import com.vaadin.tests.minitutorials.v7b9.LoginView;
import com.vaadin.tests.minitutorials.v7b9.MainView;
import com.vaadin.tests.minitutorials.v7b9.MessageView;
import com.vaadin.tests.minitutorials.v7b9.SecretView;
import com.vaadin.tests.minitutorials.v7b9.SettingsView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class NavigationtestUI extends UI
{

    Navigator navigator;

    String loggedInUser;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = NavigationtestUI.class, widgetset = "com.mycompany.vaadinviews.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

     @Override
    public void init(VaadinRequest request) {
        // Create Navigator, make it control the ViewDisplay
        navigator = new Navigator(this, this);

        // Add some Views
        // no fragment for main view
        navigator.addView(MainView.NAME,  new MainView(navigator));

        // #settings
        navigator.addView(SettingsView.NAME, new SettingsView(navigator));
        
        navigator.addView(CountView.NAME, new CountView());
        //navigator.addView(LoginView.NAME, new LoginView());
        navigator.addView(MessageView.NAME, new MessageView());
        navigator.addView(SecretView.NAME, new SecretView());
    

        navigator.addView(LoginView.NAME, new LoginView(navigator,MainView.NAME));

        navigator.addView(FieldBinderWithBeanValidation.NAME, new FieldBinderWithBeanValidation());
        
        navigator.addView(OpeningUIInPopup.NAME, new OpeningUIInPopup(navigator));
        
        navigator.navigateTo(MainView.NAME);
 
 /*
         * We use a view change handler to ensure the user is always redirected
         * to the login view if the user is not logged in.
         */
        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                System.out.println("beforeViewChange "+event.getNewView().toString()); //To change body of generated methods, choose Tools | Templates.


                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof LoginView;
                /*
                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(SimpleLoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }
                */
                
                if(((NavigationtestUI) UI.getCurrent()).getLoggedInUser()==null && !isLoginView){
                      System.out.println("=> "+LoginView.NAME);                                    
                      navigator.navigateTo(LoginView.NAME);
                }
                
                
                
                return true;
            }        

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                System.out.println("afterViewChange "+event.getNewView().toString()); //To change body of generated methods, choose Tools | Templates.
            }
        
    });
        
    }
    
    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String user) {
        loggedInUser = user;
    }
}
