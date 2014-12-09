package com.packtpub.learnvaadin;
   import java.sql.Date;
   import org.hibernate.Session;
   import com.vaadin.Application;
   import com.vaadin.data.hbnutil.HbnContainer.SessionManager;
public class SqlContainerApplication extends Application { private static final long serialVersionUID = 1L;
private transient SessionManager sessionManager;
@Override
public void init() {

       getContext().addTransactionListener
         (new CommitTransactionListener());
       // GUI code here
     }
     public synchronized SessionManager getSessionManager() {
       if (sessionManager == null) {
         sessionManager = new CurrentSessionManager();
       }
       return sessionManager;
     }
}
