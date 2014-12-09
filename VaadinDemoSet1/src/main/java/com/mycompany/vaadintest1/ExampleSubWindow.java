package com.mycompany.vaadintest1;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;



/**
 *
 * @author robertsp
 */
// Define a sub-window by inheritance
         class ExampleSubWindow extends Window {
             public ExampleSubWindow() {
                 super("Example SubWindow"); // Set window caption
                 center();
                 // Some basic content for the window
                 VerticalLayout content = new VerticalLayout();
                 content.addComponent(new Label("Just say it's OK!"));
                 content.setMargin(true);
                 setContent(content);
                 // Disable or enable the close button
                 setClosable(false); // close via OK button
                 // Trivial logic for closing the sub-window
                 Button ok = new Button("OK");
                 ok.addClickListener(new ClickListener() {
                     //public void buttonClick(ClickEvent event) {
                     //    close(); // Close the sub-window
                     //} 

                     @Override
                     public void buttonClick(Button.ClickEvent event) {
                         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                         close();
                     
                     }
                 });
                 content.addComponent(ok);
             }
}

