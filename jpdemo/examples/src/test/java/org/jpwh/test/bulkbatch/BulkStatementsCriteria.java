package org.jpwh.test.bulkbatch;

import org.jpwh.env.JPATest;
import org.jpwh.model.bulkbatch.BankAccount;
import org.jpwh.model.bulkbatch.Bid;
import org.jpwh.model.bulkbatch.CreditCard;
import org.jpwh.model.bulkbatch.CreditCard_;
import org.jpwh.model.bulkbatch.Item;
import org.jpwh.model.bulkbatch.Item_;
import org.jpwh.model.bulkbatch.User;
import org.jpwh.shared.util.CalendarUtil;
import org.jpwh.shared.util.TestData;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.*;

public class BulkStatementsCriteria extends JPATest {

    @Override
    public void configurePersistenceUnit() throws Exception {
        configurePersistenceUnit("BulkBatchPU");
    }

    public static class BulkBatchTestData {
        public TestData items;
        public TestData users;
    }

    public BulkBatchTestData storeTestData() throws Exception {
        UserTransaction tx = TM.getUserTransaction();
        tx.begin();
        EntityManager em = JPA.createEntityManager();

        Long[] itemIds = new Long[3];
        Long[] userIds = new Long[3];

        User johndoe = new User("johndoe");
        em.persist(johndoe);
        userIds[0] = johndoe.getId();

        User janeroe = new User("janeroe");
        em.persist(janeroe);
        userIds[1] = janeroe.getId();

        User robertdoe = new User("robertdoe");
        em.persist(robertdoe);
        userIds[2] = robertdoe.getId();

        Item item = new Item("Item One", CalendarUtil.TOMORROW.getTime(), johndoe);
        em.persist(item);
        itemIds[0] = item.getId();
        for (int i = 1; i <= 3; i++) {
            Bid bid = new Bid(item, robertdoe, new BigDecimal(9 + i));
            item.getBids().add(bid);
            em.persist(bid);
        }

        item = new Item("Item Two", CalendarUtil.TOMORROW.getTime(), johndoe);
        em.persist(item);
        itemIds[1] = item.getId();
        for (int i = 1; i <= 1; i++) {
            Bid bid = new Bid(item, janeroe, new BigDecimal(2 + i));
            item.getBids().add(bid);
            em.persist(bid);
        }

        item = new Item("Item_Three", CalendarUtil.AFTER_TOMORROW.getTime(), janeroe);
        em.persist(item);
        itemIds[2] = item.getId();
        for (int i = 1; i <= 1; i++) {
            Bid bid = new Bid(item, johndoe, new BigDecimal(3 + i));
            item.getBids().add(bid);
            em.persist(bid);
        }

        em.persist(new BankAccount(janeroe, "Jane Roe", "445566", "One Percent Bank Inc.", "999"));
        em.persist(new CreditCard(johndoe, "John Doe", "1234123412341234", "06", "2015"));

        tx.commit();
        em.close();

        BulkBatchTestData testData = new BulkBatchTestData();
        testData.items = new TestData(itemIds);
        testData.users = new TestData(userIds);
        return testData;
    }

    @Test
    public void bulkUpdate() throws Exception {
        BulkBatchTestData testData = storeTestData();

        UserTransaction tx = TM.getUserTransaction();
        try {
            CriteriaBuilder criteriaBuilder =
                JPA.getEntityManagerFactory().getCriteriaBuilder();

            tx.begin();
            EntityManager em = JPA.createEntityManager();

            long ITEM_ID = testData.items.getFirstId();
            long USER_ID = testData.users.getFirstId();

            Item someItem = em.find(Item.class, ITEM_ID);
            User johndoe = em.find(User.class, USER_ID);
            int originalVersion = someItem.getVersion();

            assertEquals(someItem.getSeller(), johndoe);
            assertFalse(someItem.isActive());

            CriteriaUpdate<Item> update =
                criteriaBuilder.createCriteriaUpdate(Item.class);
            Root<Item> i = update.from(Item.class);
            update.set(i.get(Item_.active), true);
            update.where(
                criteriaBuilder.equal(i.get(Item_.seller), johndoe)
            );

            int updatedEntities = em.createQuery(update).executeUpdate();

            assertEquals(updatedEntities, 2); // Entity instances, not "rows"

            assertFalse(someItem.isActive());
            em.refresh(someItem); // Update the instance in persistence context
            assertTrue(someItem.isActive());

            assertEquals(someItem.getVersion(), originalVersion); // Version wasn't incremented!

            tx.commit();
            em.close();
        } finally {
            TM.rollback();
        }
    }

    @Test
    public void bulkUpdateVersioned() throws Exception {
        BulkBatchTestData testData = storeTestData();

        UserTransaction tx = TM.getUserTransaction();
        try {
            CriteriaBuilder criteriaBuilder =
                JPA.getEntityManagerFactory().getCriteriaBuilder();

            tx.begin();
            EntityManager em = JPA.createEntityManager();

            long ITEM_ID = testData.items.getFirstId();

            Item someItem = em.find(Item.class, ITEM_ID);

            int originalVersion = someItem.getVersion();

            /* TODO: Bug in Hibernate https://hibernate.atlassian.net/browse/HHH-8434
            CriteriaUpdate<Item> update =
                criteriaBuilder.createCriteriaUpdate(Item.class);
            Root<Item> i = update.from(Item.class);
            update.set(i.get(Item_.active), true);
            update.set(
                i.get(Item_.version),
                criteriaBuilder.sum(i.get(Item_.version), 1)
            );

            int updatedEntities = em.createQuery(update).executeUpdate();

            assertEquals(updatedEntities, 3);

            em.refresh(someItem);
            assertTrue(someItem.getVersion() > originalVersion);
            */

            tx.commit();
            em.close();
        } finally {
            TM.rollback();
        }
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void bulkDelete() throws Exception {
        BulkBatchTestData testData = storeTestData();

        UserTransaction tx = TM.getUserTransaction();
        try {
            CriteriaBuilder criteriaBuilder =
                JPA.getEntityManagerFactory().getCriteriaBuilder();

            tx.begin();
            EntityManager em = JPA.createEntityManager();

            List<CreditCard> creditCards =
               em.createQuery("select c from CreditCard c").getResultList();

            CriteriaDelete<CreditCard> delete =
                criteriaBuilder.createCriteriaDelete(CreditCard.class);
            Root<CreditCard> c = delete.from(CreditCard.class);
            delete.where(
                criteriaBuilder.like(
                    c.get(CreditCard_.owner),
                    "J%"
                )
            );
            em.createQuery(delete).executeUpdate();

            for (CreditCard creditCard : creditCards) {
                em.refresh(creditCard); // Throws EntityNotFoundException
            }

            tx.commit();
            em.close();
        } finally {
            TM.rollback();
        }
    }

}
