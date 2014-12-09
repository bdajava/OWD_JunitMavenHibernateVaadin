/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */

package org.hibernate.tutorial.hbm;

import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Illustrates use of Hibernate native APIs.
 *
 * @author Steve Ebersole
 */
public class NativeApiIllustrationTest extends TestCase {
	//private SessionFactory sessionFactory;

        
    private static SessionFactory sessionFactory;
//    private static ServiceRegistry serviceRegistry;

    private static SessionFactory configureSessionFactory() throws HibernateException {
        // This is Hibernate 4.3.0 and above
        // Refer: http://www.javabeat.net/session-factory-hibernate-4/
        
        Configuration configuration = new Configuration().configure("hibernateHbn.cfg.xml"); // use nondefault named config file
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder(). applySettings(configuration.getProperties()); 
        SessionFactory sf = configuration.buildSessionFactory(builder.build()); 
        return sf;
         
    }

    public static SessionFactory getSessionFactory() {
        return configureSessionFactory();

    }        
        
        
        
        
	@Override
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application
            
        //sessionFactory = new Configuration()
        //        .configure() // configures settings from hibernate.cfg.xml
        //        .buildSessionFactory();
        sessionFactory = configureSessionFactory();
        
        ///////////
	}

	@Override
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	public void testBasicUsage() {
                System.out.println( "Running HBN Event test");
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save( new EventH( "Our very first hbn event!", new Date() ) );
		session.save( new EventH( "A follow up hbn event", new Date() ) );
		session.getTransaction().commit();
		session.close();

		// now lets pull events from the database and list them
		session = sessionFactory.openSession();
                session.beginTransaction();
                List result = session.createQuery( "from EventH" ).list();
                System.out.println( "Event rows retrieved, count:"+result.size());
		for ( EventH event : (List<EventH>) result ) {
			System.out.println( "Event (" + event.getDate() + ") : " + event.getTitle() );
		}
                session.getTransaction().commit();
                session.close();
	}
        
        
        
        
        
        
        
        
        
}
