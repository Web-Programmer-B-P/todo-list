package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import ru.job4j.models.Item;
import java.util.HashMap;
import java.util.Map;

public class HibernatePoolConnection {
    private static final Logger LOG = LogManager.getLogger(HibernatePoolConnection.class.getName());
    private static final HibernatePoolConnection INSTANCE = new HibernatePoolConnection();
    private static final String POSTGRES_SQL_DRIVER = "org.postgresql.Driver";
    private static final String URL_WITH_HOST_AND_DB_NAME = "jdbc:postgresql://localhost:5432/box?useSSL=false";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    private static final String DIALECT = "org.hibernate.dialect.PostgreSQL94Dialect";
    private static final String THREAD = "thread";
    private static final String ERROR_MESSAGE = "Смотри в конфигурацию hibernate и hikary";
    private static final String HIKARI_CONNECTION_TIMEOUT = "hibernate.hikari.connectionTimeout";
    private static final String VALUE_TIMEOUT = "20000";
    private static final String HIKARI_MINIMUM_IDLE = "hibernate.hikari.minimumIdle";
    private static final String VALUE_IDLE = "10";
    private static final String HIKARI_MAXIMUM_POOL_SIZE = "hibernate.hikari.maximumPoolSize";
    private static final String VALUE_SIZE_POOL = "20";
    private static final String HIKARI_IDLE_TIMEOUT = "hibernate.hikari.idleTimeout";
    private static final String VALUE_IDLE_TIMEOUT = "300000";
    private static final boolean FLAG_SHOW_SQL = true;
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    private HibernatePoolConnection() {

    }

    public static HibernatePoolConnection getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                Map<String, Object> settings = getSettingsForRegistry();
                registryBuilder.applySettings(settings);
                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Item.class);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                LOG.error(ERROR_MESSAGE, e);
            }
        }
        return sessionFactory;
    }

    private Map<String, Object> getSettingsForRegistry() {
        Map<String, Object> settings = new HashMap<>();
        settings.put(Environment.DRIVER, POSTGRES_SQL_DRIVER);
        settings.put(Environment.URL, URL_WITH_HOST_AND_DB_NAME);
        settings.put(Environment.USER, USER);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.DIALECT, DIALECT);
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, THREAD);
        settings.put(Environment.SHOW_SQL, FLAG_SHOW_SQL);
        settings.put(HIKARI_CONNECTION_TIMEOUT, VALUE_TIMEOUT);
        settings.put(HIKARI_MINIMUM_IDLE, VALUE_IDLE);
        settings.put(HIKARI_MAXIMUM_POOL_SIZE, VALUE_SIZE_POOL);
        settings.put(HIKARI_IDLE_TIMEOUT, VALUE_IDLE_TIMEOUT);
        return settings;
    }

    public void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
