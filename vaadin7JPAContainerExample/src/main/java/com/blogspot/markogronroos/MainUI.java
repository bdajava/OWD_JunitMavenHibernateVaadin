/*
 

http://markogronroos.blogspot.com/2012/01/jpa-and-vaadin-jpacontainer.html

Marko Grönroos
Saturday, January 21, 2012

JPA and Vaadin JPAContainer

I've been finalizing the completely rewritten chapter about Vaadin JPAContainer this week. I still need to check that everything really important is included.

I don't have almost any previous experience with JPA, which is kind of good thing when you write documentation about something. That way, you personally know all the questions a beginner would have.

JPA and ORM in general seem pretty important technologies. Whatever your application is, you most probably need some data storage. Using ordinary files is possible, but not near as handy as proper databases. However, stuffing complex class structures through the square holes of database tables was a hell before the ORM technologies emerged. XML databases and NoSQL were much nicer for structured storage.

Some years ago I did some experiments with related technologies, such as JAXB and Hibernate, but using them was a bit too much effort at that time. Using Hibernate with Vaadin really required a Container implementation, but there wasn't one at that time. Well, JPA is quite close to Hibernate, 
and Hibernate is currently one of the JPA implementations.

Vaadin JPAContainer makes things really easy.
// Create a persistent person container
JPAContainer<Person> persons =
    JPAContainerFactory.make(Person.class, "book-examples");

// You can add entities to the container as well
persons.addEntity(new Person("Marie-Louise Meilleur", 117));

// Bind it to a component
Table personTable = new Table("The Persistent People", persons);
personTable.setVisibleColumns(new String[]{"id","name","age"});
layout.addComponent(personTable);

So, just one command to get a container bound to data in a database table. JPAContainer is, however, just for binding user interface components directly to data. You can use it to add or edit the data, but using pure JPA is much nicer, easier, and more flexible for that.


// PURE JPA EXAMPLE


EntityManager em = JPAContainerFactory.
    createEntityManagerForPersistenceUnit("book-examples");
em.getTransaction().begin();
em.createQuery("DELETE FROM Person p").executeUpdate();
em.persist(new Person("Jeanne Calment", 122));
em.persist(new Person("Sarah Knauss", 119));
em.persist(new Person("Lucy Hannah", 117));
em.getTransaction().commit();
Well ok, here we got the entity manager from the JPAContainerFactory, which makes that a bit easier than normally.

JPAContainer is a commercial product. That shouldn't be a problem if you're coding for a company that can pay the license. For free software, you can use the AGPL license. I've always felt that this sort of dual-licensing scheme is most difficult for "potential startups" where you experiment with a business idea, but do not yet have a company to back it up financially. You do not normally have the money to invest in licenses at that point, and a company might not want to invest much in such experiments either.

There are of course alternatives for JPAContainer. Using pure JPA is quite OK approach as well, combined with BeanItemContainer in Vaadin. There's also SQLContainer. It is also something I don't know about, as I didn't write the chapter myself. I would need to review and reorganize it in next weeks though, and possibly rewrite at some point.

Well, I did make a couple of mistakes while writing the JPAContainer chapter. The first one was trying to just update a really complex design documentation written by someone else, in a topic I knew nothing about. Writing is always a learning experience and it's not sensible to try to write about something very complex that you don't have a clue about. Sometimes, it's best to start from scratch. Unfortunately, I didn't do that when I started, but just over a week before the release.

Another issue is learning by doing. I usually start writing documentation by experimenting how it works, making some example code. This time, as I was in a hurry, I tried to skip that phase. I only started it as the last effort when I was totally stuck, some 4 weeks of struggle behind and 2 work days before the release date. Just in those couple of days, all pieces fell into place and I finally understood how everything works.

----------

Download or get Vaadin JPAContainer add-on here: http://vaadin.com/directory#addon/vaadin-jpacontainer.



 */

/*

package alskor.vaadin.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;

public class TripManager {
    private EntityManagerFactory entityManagerFactory;

    public TripManager() {
        initDatabase();
    }

    private void initDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("alskor.vaadin.db.jpa");
    }

    public void save(Trip trip) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(trip);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Trip get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Object o = entityManager.find(Trip.class, id);
        return (Trip) o;
    }

    public Collection<Trip> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM Trip e");
        return (Collection<Trip>) query.getResultList();
    }
}




*/
/*

There is complete JPAContainer example:

https://dev.vaadin.com/svn/doc/book-examples/trunk/src/com/vaadin/book/examples/addons/jpacontainer/JPAContainerExample.java

https://dev.vaadin.com/svn/doc/book-examples/trunk/src/com/vaadin/book/examples/addons/jpacontainer/Person.java

https://dev.vaadin.com/svn/doc/book-examples/trunk/src/com/vaadin/book/examples/addons/jpacontainer/Country.java

https://dev.vaadin.com/svn/doc/book-examples/branches/vaadin-7/WebContent/WEB-INF/classes/META-INF/persistence.xml


*/
package com.blogspot.markogronroos;

import com.blogspot.markogronroos.db.Trip;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.fieldfactory.FieldFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author robertsp
 */
   

@Theme("mytheme")
@SuppressWarnings("serial")
public class MainUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class, widgetset = "com.blogspot.markogronroos.AppWidgetSet")
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
   
    MenuBar.MenuItem menuitem1 =
      menubar.addItem("JPA Examples", null, null); 
    
    MenuBar.MenuItem menuitem2 =
    menuitem1.addItem("Other Example", null, null);

    MenuBar.Command mycommand = new MenuBar.Command() {
      @Override  
      public void menuSelected(MenuBar.MenuItem selectedItem) {
        selection.setValue("Ordered a " +
                           selectedItem.getText() +
                           " from menu.");
        String selectedText = selectedItem.getText();
        if(selectedText.contains("1.1"))jpaContTable(main);
        else if(selectedText.contains("1.2"))jpaContTable2(main);
        else if(selectedText.contains("1.3"))jpaContForm1(main);
        else if(selectedText.contains("1.4"))pureJPAExample(main);
        //selectedItem.
    }  
   };    
 
    menuitem1.addItem("1.1 demo JPAContainer Table component", null, mycommand);
    menuitem1.addItem("1.2 demo JPAContainer Table component, explicit EntityManager", null, mycommand);   
    menuitem1.addItem("1.3 demo JPAContainer Form", null, mycommand);   
    
    menuitem1.addItem("1.4 demo JPA batch", null, mycommand);
    //menuitem1.addItem(menuitem2);
    

    // Define a common menu command for all the menu items.
         
      //  initEx2(request);
    }
    
    
    
    
    protected void jpaContTable(AbstractOrderedLayout main) {
    final VerticalLayout layout = new VerticalLayout();
    main.addComponent(layout);
    show(layout,"Please notice table rows are selectable, column order movable, column sort order adjustable");    
        
            
    // Create a persistent person container
    JPAContainer<Trip> trips =
        JPAContainerFactory.make(Trip.class, "source.jpa");
    // You can add entities to the container as well
    trips.addEntity(new Trip("Riga","Brussels",5.0F));
    trips.addEntity(new Trip("London","Riga",101.0F));

    // Bind it to a component
    Table personTable = new Table("Past Trips", trips);
    
    personTable.setSizeUndefined();
    personTable.setSelectable(true);
    personTable.setMultiSelect(false);
    personTable.setImmediate(true);   
    personTable.setColumnReorderingAllowed(true);
    personTable.setColumnCollapsingAllowed(true);
    
    
    personTable.setVisibleColumns(new String[]{"id","price","startLocation","finishLocation"});
    layout.addComponent(personTable);
        
    Button button = new Button("Clear");
    button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //layout.addComponent(new Label("Thank you for clicking"));
                layout.removeAllComponents();
            }
    });
    layout.addComponent(button);
    }
    
 
    protected void jpaContTable2(AbstractOrderedLayout main) {
    final VerticalLayout layout = new VerticalLayout();
    main.addComponent(layout);
    show(layout,"This is same as above but EntityManager is created explicitly");    
        
    EntityManager em = JPAContainerFactory.createEntityManagerForPersistenceUnit("source.jpa");   
    /*
  
       Notice that if you use update the persistent data with an entity manager outside a JPAContainer bound to the 
       data, you need to refresh the container as described in Section 19.4.2, “Creating and Accessing Entities”.

    Refreshing JPAContainer
    
    In cases where you change JPAContainer items outside the container, for example by through an EntityManager, 
    or when they change in the database, you need to refresh the container.
    The EntityContainer interface implemented by JPAContainer provides two methods to refresh a container. The 
    refresh() discards all container caches and buffers and refreshes all loaded items in the container. All 
    changes made to items provided by the container are discarded. The refreshItem() refreshes a single item.
    
    */
    // Create a persistent person container
    JPAContainer<Trip> trips =
        JPAContainerFactory.make(Trip.class, em);

    // You can add entities to the container as well
    trips.addEntity(new Trip("Riga","Brussels",5.0F));
    trips.addEntity(new Trip("London","Riga",101.0F));

    // Bind it to a component
    Table personTable = new Table("Past Trips", trips);
    
    personTable.setSizeUndefined();
    personTable.setSelectable(true);
    personTable.setMultiSelect(false);
    personTable.setImmediate(true);   
    personTable.setColumnReorderingAllowed(true);
    personTable.setColumnCollapsingAllowed(true);
    
    
    personTable.setVisibleColumns(new String[]{"id","price","startLocation","finishLocation"});
    layout.addComponent(personTable);
        
     Button button = new Button("Clear");
    button.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                //layout.addComponent(new Label("Thank you for clicking"));
                layout.removeAllComponents();
            }
    });
    layout.addComponent(button);
    }
    

protected void jpaContForm1(AbstractOrderedLayout main) {
    final VerticalLayout layout = new VerticalLayout();
    main.addComponent(layout);
    show(layout,"JPAContainer Form Example enterd!");        
    
// Have a persistent container
    final JPAContainer<Trip> trips =
             JPAContainerFactory.make(Trip.class, "source.jpa");
         // For selecting an item to edit
         // Country Editor
    final Form  tripForm  = new Form();
    tripForm.setCaption("Trip form");

    tripForm.setWidth("420px");
    tripForm.setBuffered(true);
    tripForm.setEnabled(false);
    Object itemId = trips.getIdByIndex(0);
    if(itemId==null)show(layout,"Sorry no item retrieved!");
    else{
    show(layout," choosen item: "+itemId); //+" class: "+itemId.getClass().getName());
    Item tripItem =
            trips.getItem(itemId);
    show(layout," Item found :"+tripItem);
    layout.addComponent(tripForm);


        // Use a JPAContainer field factory
        //  - no configuration is needed here
        final FieldFactory fieldFactory = new FieldFactory();
        //fieldFactory.
        tripForm.setFormFieldFactory(fieldFactory);
        // Edit the item in the form
        tripForm.setItemDataSource(tripItem);
        tripForm.setEnabled(true);
        
        tripForm.setVisibleItemProperties(new String[] { "startLocation", "finishLocation","status" });        
        //tripForm.getField("name").setCaption("name new caption");
        
        
        
        
        // Handle saves on the form
        final Button save = new Button("Save");
        tripForm.getFooter().addComponent(save);
        save.addClickListener(new ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent event) {
                try {
                    tripForm.commit();
                    tripForm.setEnabled(false);
                } catch (InvalidValueException e) {
                    e.printStackTrace();
                }
        }
        });

    
    }    
    
}    
    
 
protected void pureJPAExample(AbstractOrderedLayout main) {
  final VerticalLayout layout = new VerticalLayout();
  main.addComponent(layout);
  show(layout,"Entering pureJPAExample");  
  EntityManager em = JPAContainerFactory.
    createEntityManagerForPersistenceUnit("source.jpa");
  em.getTransaction().begin();
  em.createQuery("DELETE FROM Trip t").executeUpdate();
  show(layout,"Rows deleted!"); 
  em.persist(new Trip("Istambul","Mumbai",555.0F));
  em.persist(new Trip("Mumbai","Brussels",125.0F));
  show(layout,"2 rows added!"); 
  em.getTransaction().commit();
  show(layout,"Commit completed!"); 

}








void show(AbstractOrderedLayout l,String text){
 l.addComponent(new Label(text)); 

}

}