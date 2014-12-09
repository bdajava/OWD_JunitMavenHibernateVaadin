package com.mycompany.vaadintest1;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.mycompany.vaadintest1.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        
    final VerticalLayout main = new VerticalLayout();
    main.setMargin(true);
    setContent(main);
          
    //Window main = new Window("Test Application");
       
    // Create a menu bar
    final MenuBar menubar = new MenuBar();
    main.addComponent(menubar);  
    
    // A feedback component
    final Label selection = new Label("-");
    main.addComponent(selection);
   
    MenuBar.MenuItem examples =
      menubar.addItem("Examples", null, null); 
    
    //MenuBar.MenuItem example1 =
    //examples.addItem("Example 1", null, null);

    MenuBar.Command mycommand = new MenuBar.Command() {
      @Override  
      public void menuSelected(MenuItem selectedItem) {
        selection.setValue("Ordered a " +
                           selectedItem.getText() +
                           " from menu.");
        String selectedText = selectedItem.getText();
        if(selectedText.contains("1.1"))initEx1(main);
        else if(selectedText.contains("1.2"))initEx2(main);
        else if(selectedText.contains("1.3"))initEx3(main);
        else if(selectedText.contains("1.4"))initEx4(main);
        else if(selectedText.contains("1.5"))initEx5(main);
        else if(selectedText.contains("1.6"))initEx6(main);
        else if(selectedText.contains("1.7"))initEx7(main);
        //selectedItem.
    }  
   };    
 
    examples.addItem("1.1 Default", null, mycommand);
    examples.addItem("1.2 Funny phrase", null, mycommand);
    examples.addItem("1.3 Links", null, mycommand);
    examples.addItem("1.4 Panel", null, mycommand);
    examples.addItem("1.5 Sub-Window", null, mycommand);
    examples.addItem("1.6 Sub-Window-as-subclass", null, mycommand);
    examples.addItem("1.7 Form example", null, mycommand);
   
  

    // Define a common menu command for all the menu items.
         
      //  initEx2(request);
    }
    
    
    
    
    protected void initEx1(AbstractOrderedLayout main) {
        final VerticalLayout layout = new VerticalLayout();
        main.addComponent(layout);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);
    }
    
   
 protected void initEx2(AbstractOrderedLayout main) {
        final VerticalLayout layout = new VerticalLayout();
        main.addComponent(layout);
        //layout.setMargin(true);
        //setContent(layout);
 
 final TextField name1 = new TextField("Somebody's name"); 
 final TextField name2 = new TextField("somebody's name"); 
 layout.addComponent(name1);
 layout.addComponent(name2);        
        
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
              String phrase = getFunnyPhraseEx2( name1.getValue(), name2.getValue());
              layout.addComponent(new Label(phrase));
            }
        });
        layout.addComponent(button);
    }   
    
    
    
 

public String getFunnyPhraseEx2(String name1, String name2) {
         String[] verbs = new String[] {
           "eats", "melts", "breaks", "washes", "sells"};
         String[] bodyParts = new String[] {
           "head", "hands", "waist", "eyes", "elbows"};
         return name1 + " " +
           verbs[(int) (Math.random() * 100 % verbs.length)] + " " +
           name2 + "'s " +
           bodyParts[(int) (Math.random() * 100 % verbs.length)];
} 
 
protected void initEx3(AbstractOrderedLayout main) {
        final VerticalLayout layout = new VerticalLayout();
        main.addComponent(layout);
   /*     
        
   Theme Resources
   Theme resources of ThemeResource class are files, typically images, included in a theme. A theme is located with the path VAADIN/themes/themename in a web application. The name of a theme resource is given as the parameter for the constructor, with a path relative to the theme folder.
         // A theme resource in the current theme ("mytheme")
         // Located in: VAADIN/themes/mytheme/img/themeimage.png
         ThemeResource resource = new ThemeResource("img/themeimage.png");
         // Use the resource
         Image image = new Image("My Theme Image", resource);
        
        
        
  */      
  // Textual link
  Link link = new Link("Vaadin",  new ExternalResource("http://vaadin.com/"));
  layout.addComponent(link);
  //
  // Image + caption
  //
  link = new Link("To appease both literal and visual",
        new ExternalResource("http://vaadin.com/"));
  link.setIcon(new ThemeResource("img/java_duke.001.jpg"));
  layout.addComponent(link);
 //
 // image + new window/tab into new window/tab and icon after caption.
 // 
link = new Link("Take me a away to a faraway land",
        new ExternalResource("http://vaadin.com/"));
// Open the URL in a new window/tab
link.setTargetName("_blank");
// Indicate visually that it opens in a new window/tab
link.setIcon(new ThemeResource("img/java_duke.002.jpg"));
link.addStyleName("icon-after-caption"); 
layout.addComponent(link);  
}


protected void initEx4(AbstractOrderedLayout main) {
  final VerticalLayout layout = new VerticalLayout();
  main.addComponent(layout);
  Panel panel = new Panel("Astronomy Panel");
  panel.addStyleName("mypanelexample");
  panel.setSizeUndefined(); // Shrink to fit content
  layout.addComponent(panel);
  // Create the content
  FormLayout content = new FormLayout();
  content.addStyleName("mypanelcontent");
  content.addComponent(new TextField("Participant"));
  content.addComponent(new TextField("Organization"));
  content.setSizeUndefined(); // Shrink to fit
  content.setMargin(true);
  panel.setContent(content);
}

protected void initEx5(AbstractOrderedLayout main) {
  final VerticalLayout layout = new VerticalLayout();
  main.addComponent(layout);
  // Create a sub-window and set the content
  Window subWindow = new Window("Sub-window");
  VerticalLayout subContent = new VerticalLayout();
  subContent.setMargin(true);
  subWindow.setContent(subContent);
// Put some components in it
  subContent.addComponent(new Label("Meatball sub"));
  subContent.addComponent(new Button("Awlright"));
  // Center it in the browser window
  subWindow.center();
  // Open it in the UI
  addWindow(subWindow);

}


protected void initEx6(AbstractOrderedLayout main) {
  final VerticalLayout layout = new VerticalLayout();
  main.addComponent(layout);
  // Create a sub-window and set the content
 // Some UI logic to open the sub-window
         final Button open = new Button("Open Sub-Window");
         layout.addComponent(open);
         open.addClickListener(new ClickListener() {

         @Override
         public void buttonClick(ClickEvent event) {
                 ExampleSubWindow sub = new ExampleSubWindow();
                 UI.getCurrent().addWindow(sub);
             }
});


}


protected void initEx7(AbstractOrderedLayout main) {
  final VerticalLayout layout = new VerticalLayout();
  main.addComponent(layout);
        layout.setMargin(true);


        final FieldGroup fieldGroup = new BeanFieldGroup<Person>(Person.class);

        // We need an item data source before we create the fields to be able to
        // find the properties, otherwise we have to specify them by hand
        final Person person = new Person("John","Doe", 34);
        fieldGroup.setItemDataSource(new BeanItem<Person>(person));

        // Loop through the properties, build fields for them and add the fields
        // to this root
        for (Object propertyId : fieldGroup.getUnboundPropertyIds()) {
            layout.addComponent(fieldGroup.buildAndBind(propertyId));
        }
        layout.addComponent(new Button("Show person", new ClickListener() {
             @Override
             public void buttonClick(ClickEvent event) {
                 try {
                     fieldGroup.commit();
                 } catch (FieldGroup.CommitException ex) {
                     Logger.getLogger(MyVaadinUI.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 Notification.show(person.toString());
             }
}));

    }







}
