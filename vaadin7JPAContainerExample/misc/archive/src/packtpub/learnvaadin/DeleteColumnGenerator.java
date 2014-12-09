/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.packtpub.learnvaadin;
       import com.vaadin.data.Property;
       import com.vaadin.data.Property.ValueChangeEvent;
       import com.vaadin.data.Property.ValueChangeListener;
       import com.vaadin.ui.Table;
       import com.vaadin.ui.Table.ColumnGenerator;
       import org.hibernate.Session;
       import com.vaadin.data.hbnutil.HbnContainer;
//       import com.vaadin.data.hbnutil.HbnContainer.SessionManager;
       import com.vaadin.ui.Component;
       import com.vaadin.ui.Select;
       import com.vaadin.ui.Table;
       import com.vaadin.ui.Table.ColumnGenerator;






@SuppressWarnings("serial")
class DeleteColumnGenerator implements ColumnGenerator {


    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       @SuppressWarnings("serial")
       class JobColumnGenerator implements ColumnGenerator {
         private SqlContainerApplication application;
         JobColumnGenerator(SqlContainerApplication application) {
           this.application = application;
         }
         @Override
         public Component generateCell(final Table source, final
           Object itemId, Object columnId) {
         HbnContainer<Job> jobContainer = new
           HbnContainer<Job>(Job.class, application.getSessionManager());
           final Select select = new Select("", jobContainer);
           select.setItemCaptionPropertyId("label");
           select.setImmediate(true);
           Property jobProperty =
             source.getItem(itemId).getItemProperty("job");
           Long jobId = (Long) jobProperty.getValue();
           if (jobId != null) {
             select.select(jobId);
           }
           select.addListener(new ValueChangeListener() {
             @Override
             public void valueChange(ValueChangeEvent event) {
               Long jobId = (Long) event.getProperty().getValue();
               Job job = null;
               SessionManager sessionManager =
                application.getSessionManager();
               Session session = sessionManager.getSession();
               Person person = (Person) session.get(Person.class,
                (Long) itemId);

if (jobId != null) {
          job = (Job) session.get(Job.class, jobId);
}
        person.setJob(job);
      }
});
    return select;
  }
       }
}