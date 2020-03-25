package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.job4j.models.Item;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemDao {
    private static final Logger LOG = LogManager.getLogger(ItemDao.class.getName());
    private static final HibernatePoolConnection POOL_CONNECTION = HibernatePoolConnection.getInstance();
    private static final ItemDao INSTANCE = new ItemDao();
    private static final String ERROR_MESSAGE_ADD = "Смотри в добавление новой задачи";
    private static final String ERROR_MESSAGE_FIND_ALL = "Смотри в получение списка всех заданий";
    private static final String ERROR_MESSAGE_FIND_BY_STATUS = "Смотри в выборку всех заданий по статусу активности";
    private static final String ERROR_MESSAGE_DELETE = "Смотри в удаление записи";
    private static final String ERROR_MESSAGE_UPDATE = "Смотри в обновление записи";
    private static final String QUERY_ALL_ITEMS = "From Item";
    private static final String QUERY_ALL_ACTIVE_ITEMS = "From Item WHERE done=:done";
    private static final boolean FLAG_FOR_ACTIVE_ITEM = false;

    private ItemDao() {

    }

    public static ItemDao getInstance() {
        return INSTANCE;
    }

    public void add(Item item) {
        transactionWithOutResult(
                session -> session.save(item),
                ERROR_MESSAGE_ADD
        );
    }

    public void update(Item item) {
        transactionWithOutResult(session -> session.update(item),
                ERROR_MESSAGE_UPDATE
        );
    }

    public void delete(Item item) {
        transactionWithOutResult(session -> session.delete(item),
                ERROR_MESSAGE_DELETE
        );
    }

    public List<Item> findAll() {
        return transactionWithResult(
                session -> session.createQuery(QUERY_ALL_ITEMS).list(),
                ERROR_MESSAGE_FIND_ALL
        );
    }


    public List<Item> findAllActive() {
        return transactionWithResult(
                session -> {
                    Query query = session.createQuery(QUERY_ALL_ACTIVE_ITEMS);
                    query.setParameter("done", FLAG_FOR_ACTIVE_ITEM);
                    return query.list();
                },
                ERROR_MESSAGE_FIND_BY_STATUS
        );
    }

    private <T> T transactionWithResult(final Function<Session, T> command, String message) {
        T result = null;
        Transaction transaction = null;
        try (final Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = command.apply(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(message, e);
        }
        return result;
    }

    private void transactionWithOutResult(final Consumer<Session> command, String message) {
        Transaction transaction = null;
        try (final Session session = POOL_CONNECTION.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            command.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(message, e);
        }
    }
}
