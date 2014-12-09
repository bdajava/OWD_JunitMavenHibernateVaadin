/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.learnvaadin;

   import com.vaadin.data.hbnutil.HbnContainer;
//   import com.vaadin.data.hbnutil.HbnContainer.SessionManager;
   import com.vaadin.ui.HorizontalLayout;
   import com.vaadin.ui.Table;
   import com.vaadin.ui.VerticalLayout;
   import com.vaadin.ui.Window;




   public class MainWindow extends Window {
     private static final long serialVersionUID = 1L;
     private Table table;
     public MainWindow() {
       VerticalLayout vLayout = new VerticalLayout();
       vLayout.setMargin(true);
       setContent(vLayout);
       table = new Table();
       table.setPageLength(10);
       table.setEditable(true);
       table.setSizeFull();
       table.addGeneratedColumn("delete", new DeleteColumnGenerator());
       vLayout.addComponent(table);
       HorizontalLayout hLayout = new HorizontalLayout();
       hLayout.setMargin(true);
       hLayout.setSpacing(true);
       vLayout.addComponent(hLayout);
     }
     void initialize() {
       SqlContainerApplication app = (SqlContainerApplication)
         getApplication();
       SessionManager sessionManager = app.getSessionManager();
       HbnContainer<Person> personContainer = new
         HbnContainer<Person>(Person.class, sessionManager);
       table.setContainerDataSource(personContainer);
       table.addGeneratedColumn("job", new
        JobColumnGenerator((SqlContainerApplication) getApplication()));
       table.setVisibleColumns(new Object[] { "firstName", "lastName",
         "birthdate", "job", "delete" });
} }
