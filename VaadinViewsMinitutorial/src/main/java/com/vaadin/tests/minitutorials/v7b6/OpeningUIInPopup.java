package com.vaadin.tests.minitutorials.v7b6;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.tests.minitutorials.v7b9.CountView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class OpeningUIInPopup extends Panel implements View {

    public static final String NAME = "OpeningUIInPopup";

 

    public OpeningUIInPopup(final Navigator navigator) {

        //super(new VerticalLayout());
    
        Layout layout = new VerticalLayout();   
        
        setCaption("OpeningUIinPopup");
        
        
        Button popupButton = new Button("Open popup with MyPopupUI");

        BrowserWindowOpener popupOpener = new BrowserWindowOpener(
                MyPopupUI.class);  // notice this should be class extending UI
        popupOpener.setFeatures("height=300,width=300");
        popupOpener.extend(popupButton);

        // Add a parameter
        popupOpener.setParameter("foo", "bar");

        // Set a fragment
        popupOpener.setUriFragment("myfragment");
        
        layout.addComponent(popupButton);
        
        setContent(layout);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        

// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
