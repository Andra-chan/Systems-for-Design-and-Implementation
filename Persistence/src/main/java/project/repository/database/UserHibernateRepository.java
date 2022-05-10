package project.repository.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import project.model.Flight;
import project.model.Ticket;
import project.model.User;
import project.repository.ITicketRepository;
import project.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserHibernateRepository implements IUserRepository {

    private static final Logger logger = LogManager.getLogger();
    private SessionFactory sessionFactory;

    //public UserHibernateRepository(Properties props) {
       // logger.info("Initializing UserHibernateRepository with properties: {} ", props);
    //}

    public UserHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*
    public void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
     */

    @Override
    public User save(User entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            System.out.println("Session: " + session);
            try {
                System.out.println("in try0");
                tx = session.beginTransaction();
                System.out.println("in try1");
                session.save(entity);
                System.out.println("in try2");
                tx.commit();
                System.out.println("in try3");
                return entity;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println("in catch");
            }
            return null;
        }
    }


    @Override
    public User update(User entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                User user = session.load(User.class, entity.getId());

                user.setFirstName(entity.getFirstName());
                user.setLastName(entity.getLastName());
                user.setEmail(entity.getEmail());
                user.setPassword(entity.getPassword());

                session.update(user);
                tx.commit();

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return entity;
    }

    @Override
    public User delete(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                User user = session.createQuery("from User where id=" + id, User.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Stergem user-ul " + user.getId());
                session.delete(user);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public User findOne(Integer id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                user = session.createQuery("from User where id=" + id, User.class).getSingleResult();
                System.out.println("User found: " + user);

                tx.commit();
                return user;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println("---------------------- ERROR ---------------------");
                System.out.println(ex);
            }
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                users = session.createQuery("from User", User.class).list();

                System.out.println("Users found: ");
                for (User u : users) {
                    System.out.println(u.getFirstName() + ", " + u.getLastName());
                }
                tx.commit();
                return users;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public User findOneLogin(String email, String password) {
        return null;
    }

    @Override
    public List<Flight> findBySearch(String searchDestination, LocalDateTime searchDateTime) {
        return null;
    }

    @Override
    public List<Flight> findBySearchAirport(String searchDestination, String searchAirport) {
        return null;
    }
}
