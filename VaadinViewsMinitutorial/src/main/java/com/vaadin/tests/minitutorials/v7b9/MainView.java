/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vaadin.tests.minitutorials.v7b9;

import com.mycompany.vaadinviews.FieldBinderWithBeanValidation;
import com.mycompany.vaadinviews.NavigationtestUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.tests.minitutorials.v7b6.OpeningUIInPopup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainView extends Panel implements View {

    public static final String NAME = "";

    private Button logOut;

    public MainView(final Navigator navigator) {

        VerticalLayout layout = new VerticalLayout();

        
        
        Link lnk = new Link("FieldBinderWithBeanValidation",
                new ExternalResource("#!" + FieldBinderWithBeanValidation.NAME));
        layout.addComponent(lnk);        
        
        lnk = new Link("Opening UI in Popup",
                new ExternalResource("#!" + OpeningUIInPopup.NAME));
        layout.addComponent(lnk);
                
        
        lnk = new Link("Count",
                new ExternalResource("#!" + CountView.NAME));
        layout.addComponent(lnk);

        lnk = new Link("Message: Hello", new ExternalResource("#!"
                + MessageView.NAME + "/Hello"));
        layout.addComponent(lnk);

        lnk = new Link("Message: Bye", new ExternalResource("#!"
                + MessageView.NAME + "/Bye/Goodbye"));
        layout.addComponent(lnk);

        lnk = new Link("Private message: Secret", new ExternalResource("#!"
                + SecretView.NAME + "/Secret"));
        layout.addComponent(lnk);

        lnk = new Link("Private message: Topsecret", new ExternalResource("#!"
                + SecretView.NAME + "/Topsecret"));
        layout.addComponent(lnk);

        lnk = new Link("Opening UI in Popup",
                new ExternalResource("#!" + OpeningUIInPopup.NAME));
        layout.addComponent(lnk);
        
        
        
        // login/logout toggle so we can test this
        Button logInOut = new Button("Toggle login",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        Object user = ((NavigationtestUI) UI.getCurrent())
                                .getLoggedInUser();
                       NavigationtestUI m = (NavigationtestUI) UI.getCurrent();
                       
                                m.setLoggedInUser(user == null ? "Smee" : null);
                        Notification.show("Logged in user is: "+m.getLoggedInUser());
                    }
                });
        layout.addComponent(logInOut);        setContent(layout);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }
}
