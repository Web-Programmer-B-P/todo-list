package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.models.Item;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private static final Logger LOG = LogManager.getLogger(ItemDao.class.getName());
    private static final HibernatePoolConnection POOL_CONNECTION = HibernatePoolConnection.getInstance();
    private static final ItemDao INSTANCE = new ItemDao();
    private static final String ERROR_MESSAGE_ADD = "Смотри в добавление новой задачи";
    private static final String ERROR_MESSAGE_FIND_ALL = "Смотри в получение списка всех заданий";
    private static final String ERROR_MESSAGE_FIND_BY_STATUS = "Смотри в выборку всех заданий по статусу активности";
    private static final String ERROR_MESSAGE_DELETE = "Смотри в удаление записи";
    private static final String ERROR_MESSAGE_UPDATE = "Смотри в обновление записи";

    private ItemDao() {

    }

    public static ItemDao getInstance() {
        return INSTANCE;
    }

    public void add(Item item) {
        Transaction transaction = null;
        try (Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(ERROR_MESSAGE_ADD, e);
        }
    }

    public void update(Item item) {
        Transaction transaction = null;
        try (Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(ERROR_MESSAGE_UPDATE, e);
        }
    }

    public void delete(Item item) {
        Transaction transaction = null;
        try (Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(ERROR_MESSAGE_DELETE, e);
        }
    }

    public List<Item> findAll() {
        List<Item> allItems = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            allItems = session.createQuery("From Item").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(ERROR_MESSAGE_FIND_ALL, e);
        }
        return allItems;
    }

    public List<Item> findAllActive() {
        List<Item> activeItems = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            activeItems = session.createQuery("From Item WHERE done=false").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(ERROR_MESSAGE_FIND_BY_STATUS, e);
        }
        return activeItems;
    }
}
