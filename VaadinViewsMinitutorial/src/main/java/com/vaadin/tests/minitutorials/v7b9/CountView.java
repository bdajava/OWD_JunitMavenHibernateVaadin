/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package com.vaadin.tests.minitutorials.v7b9;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class CountView extends Panel implements View {
    public static final String NAME = "count";

    private static int count = 1;

    public CountView() {
        setContent(new Label("Created: " + count++));
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
