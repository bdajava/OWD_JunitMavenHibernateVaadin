/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.vaadinviews;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author robertsp
 */
public class MyVerticalLayout extends VerticalLayout implements View{

 public MyVerticalLayout(){
     
          Link lnk = new Link("FieldBinderWithBeanValidation",
                new ExternalResource("#!" + FieldBinderWithBeanValidation.NAME));
          addComponent(lnk);        
   
 }   
    
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
