package org.jpwh.test.conversation;

import org.jpwh.env.JPATest;
import org.jpwh.model.conversation.User;
import org.jpwh.model.conversation.Item;
import org.jpwh.model.conversation.Image;
import org.jpwh.shared.util.TestData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

public class Detached extends JPATest {

    @Override
    public void configurePersistenceUnit() throws Exception {
        configurePersistenceUnit("ConversationPU");
    }

    @Test
    public void businessKeyEquality() throws Exception {
        UserTransaction tx = TM.getUserTransaction();
        try {
            tx.begin();
            EntityManager em = JPA.createEntityManager();
            User user = new User("johndoe");
            em.persist(user);
            tx.commit();
            em.close();

            Long USER_ID = user.getId();

            tx.begin();
            em = JPA.createEntityManager();

            User a = em.find(User.class, USER_ID);
            User b = em.find(User.class, USER_ID);
            assertTrue(a == b);
            assertTrue(a.equals(b));
            assertEquals(a.getId(), b.getId());

            tx.commit();
            em.close();

            // Now compare with detached instances...
            tx.begin();
            em = JPA.createEntityManager();

            User c = em.find(User.class, USER_ID);
            assertFalse(a == c); // Still false, of course!
            assertTrue(a.equals(c)); // Now true!
            assertEquals(a.getId(), c.getId());

            tx.commit();
            em.close();

            Set<User> allUsers = new HashSet();
            allUsers.add(a);
            allUsers.add(b);
            allUsers.add(c);
            assertEquals(allUsers.size(), 1); // Correct!

        } finally {
            TM.rollback();
        }
    }

    @Test
    public void conversationCreateItem() throws Exception {
        Item item = new Item("Some Item");
        item.getImages().add(new Image("Foo", "foo.jpg", 800, 600));

        CreateEditItemService event = new CreateEditItemService();
        item = event.storeItem(item);
        event.commit();

        {
            UserTransaction tx = TM.getUserTransaction();
            try {
                tx.begin();
                EntityManager em = JPA.createEntityManager();
                item = em.find(Item.class, item.getId());
                assertEquals(item.getName(), "Some Item");
                assertEquals(item.getImages().size(), 1);
            } finally {
                TM.rollback();
            }
        }
    }

    @Test
    public void conversationEditItem() throws Exception {
        final TestData testData = storeItemImagesTestData();
        Long ITEM_ID = testData.getFirstId();

        // First event in conversation
        CreateEditItemService firstEvent = new CreateEditItemService();
        Item item = firstEvent.getItemImages(ITEM_ID);
        firstEvent.commit();

        // Detached data can be modified
        item.setName("New Name");
        item.getImages().add(new Image("Foo", "foo.jpg", 800, 600));

        // Possibly more events, loading necessary data into conversation...

        // Final event in conversation
        CreateEditItemService finalEvent = new CreateEditItemService();
        item = finalEvent.storeItem(item);
        finalEvent.commit();

        {
            UserTransaction tx = TM.getUserTransaction();
            try {
                tx.begin();
                EntityManager em = JPA.createEntityManager();
                item = em.find(Item.class, item.getId());
                assertEquals(item.getName(), "New Name");
                assertEquals(item.getImages().size(), 4);
            } finally {
                TM.rollback();
            }
        }
    }

    public class CreateEditItemService {

        final UserTransaction tx;
        final EntityManager em;

        /* 
           The <code>CreateEditItemService</code> class has one instance per event, associated
           with one persistence context and transaction. You begin the transaction and create
           the <code>EntityManager</code> when the service is created, when an event has to
           be handled by the service.
         */
        public CreateEditItemService() throws Exception {
            tx = TM.getUserTransaction();
            tx.begin();
            em = JPA.createEntityManager();
        }

        /* 
           This method handles the "load an <code>Item</code> for editing" event. With the
           <code>EntityManager</code> you create a query for an auction <code>Item</code>
           and also eagerly load its <code>images</code> collection (a set of embeddables,
           mapped as shown earlier in <a href="#CollectionComponents"/>). You'll see more
           examples of JPA queries later, in <a href="#CreatingExecutingQueries"/>.
         */
        public Item getItemImages(Long itemId) {
            return (Item)em.createQuery(
                "select i from Item i " +
                    "left join fetch i.images where i.id = :id"
            ).setParameter("id", itemId).getSingleResult();
        }

        /* 
            This method handles the "store an <code>Item</code> after editing" event. You
            call <code>merge()</code> to make a detached or new, transient <code>Item</code>
            and its dependent collection of <code>images</code> persistent. Remember that
            merging returns a persistent instance, as discussed in <a href="#Merging"/>. The
            client should now continue working with the returned "current" <code>Item</code>.
         */
        public Item storeItem(Item item) {
            return em.merge(item);
        }

        /* 
           Part of the service contract is that the client must call this
           <code>commit()</code> method after an event has been processed. This
           method will flush the persistence context, commit the transaction, and
           close the <code>EntityManager</code>.
         */
        public void commit() throws Exception {
            try {
                tx.commit();
            } finally {
                em.close();
            }
        }
    }

    /* ################################################################################### */

    public TestData storeItemImagesTestData() throws Exception {
        UserTransaction tx = TM.getUserTransaction();
        tx.begin();
        EntityManager em = JPA.createEntityManager();
        Long[] ids = new Long[1];
        Item item = new Item();
        item.setName("Some Item");
        em.persist(item);
        ids[0] = item.getId();
        for (int i = 1; i <= 3; i++) {
            item.getImages().add(
                new Image("Image " + i, "image" + i + ".jpg", 640, 480));
        }
        tx.commit();
        em.close();
        return new TestData(ids);
    }

}
