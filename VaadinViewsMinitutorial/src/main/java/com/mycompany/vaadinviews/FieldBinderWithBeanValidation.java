/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package com.mycompany.vaadinviews;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.tests.data.bean.Address;
import com.vaadin.tests.data.bean.Country;
import com.vaadin.tests.data.bean.PersonWithBeanValidationAnnotations;
import com.vaadin.tests.data.bean.Sex;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FieldBinderWithBeanValidation extends Panel implements View { 
    
    private TextArea lastName;
    private TextField email;
    private TextField age;
    private Table sex;
    private TextField deceased;
    private TextField firstName;

    public static final String NAME = "fieldbinderwithbeanvalidation";
    
    
    public FieldBinderWithBeanValidation() {
        //addComponent(log);
        //this.
        //getContent().
        Layout layout = new VerticalLayout();
        final BeanFieldGroup<PersonWithBeanValidationAnnotations> fieldGroup = new BeanFieldGroup<PersonWithBeanValidationAnnotations>(
                PersonWithBeanValidationAnnotations.class);

        fieldGroup.buildAndBindMemberFields(this);
        //fieldGroup.
        //Layout hlayout = new HorizontalLayout();
        layout.addComponent(firstName);
        //layout.addComponent(hlayout);
        layout.addComponent(lastName);
        layout.addComponent(email);
        layout.addComponent(age);
        layout.addComponent(sex);
        layout.addComponent(deceased);

        Button commitButton = new Button("Commit", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                String msg = "Commit succesful";
                try {
                    fieldGroup.commit();
                } catch (CommitException e) {
                    msg = "Commit failed: " + e.getMessage();
                }
                Notification.show(msg);
                //log.log(msg);
            }
        });
        
        
        Button discardButton = new Button("Discard",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        fieldGroup.discard();
                        //log.log("Discarded changes");

                    }
                });
        Button showBean = new Button("Show bean values",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        Notification.show(getPerson(fieldGroup).toString());

                    }
                });
        layout.addComponent(commitButton);
        layout.addComponent(discardButton);
        layout.addComponent(showBean);
        sex.setPageLength(0);

        PersonWithBeanValidationAnnotations p = new PersonWithBeanValidationAnnotations(
                "John", "Doe", "john@doe.com", 64, Sex.MALE, new Address(
                        "John street", 11223, "John's town", Country.USA));
        fieldGroup
                .setItemDataSource(new BeanItem<PersonWithBeanValidationAnnotations>(
                        p));
        setContent(layout);
    }

    public static PersonWithBeanValidationAnnotations getPerson(
            FieldGroup binder) {
        return ((BeanItem<PersonWithBeanValidationAnnotations>) binder
                .getItemDataSource()).getBean();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
