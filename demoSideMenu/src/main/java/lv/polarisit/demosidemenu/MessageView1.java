/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lv.polarisit.demosidemenu;



import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class MessageView1 extends Panel implements View {
    public static final String NAME = "message1";
    private Layout layout;

    public MessageView1() {
        super(new VerticalLayout());
        setCaption("Messages1");
    }

    @Override
    public void enter(ViewChangeEvent event) {
        
        ((Layout) getContent()).addComponent(new Label("Hello from MessageView1"));
        if (event.getParameters() != null) {
            // split at "/", add each part as a label
            String[] msgs = event.getParameters().split("/");
            //for (String msg : msgs) {
            //    ((Layout) getContent()).addComponent(new Label("There is no messages yet :)"));
            //}
        }
    }
}
