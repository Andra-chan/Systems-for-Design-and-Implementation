package project.repository.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import project.model.Flight;
import project.model.Ticket;
import project.repository.ITicketRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TicketHibernateRepository implements ITicketRepository {

    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;

    public TicketHibernateRepository(Properties props) {
        logger.info("Initializing TicketDbRepository with properties: {} ", props);
    }

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

    @Override
    public Ticket save(Ticket entity) {
        try {
            initialize();
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
                    return null;
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                    System.out.println("in catch");
                } catch (Exception exception) {
                    logger.error(exception);
                    System.out.println("Error DB " + exception);
                }
            }
        }finally {
            close();
            logger.traceExit();
        }
        return null;
    }

    @Override
    public Ticket update(Ticket entity) {
        try{
            initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Ticket ticket = session.load( Ticket.class, entity.getId());

                ticket.setName(entity.getName());
                ticket.setTourists(entity.getTourists());
                ticket.setAddress(entity.getAddress());
                ticket.setSeats(entity.getSeats());
                ticket.setFlightId(entity.getFlightId());

                session.update(ticket);
                tx.commit();

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            } catch (Exception exception) {
                logger.error(exception);
                System.out.println("Error DB " + exception);
            }
        }
    }finally {
        close();
        logger.traceExit();
    }
        return entity;
}

    @Override
    public Ticket delete(Integer id) {
        try{
            initialize();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Ticket ticket = session.createQuery("from Ticket where id"+id, Ticket.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Stergem ticket-ul " + ticket.getId());
                session.delete(ticket);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            } catch (Exception exception) {
                logger.error(exception);
                System.out.println("Error DB " + exception);
            }
        }
        }finally {
            close();
            logger.traceExit();
        }
        return null;
    }

    @Override
    public Ticket findOne(Integer id) {
        try{
            initialize();
        try (Session session = sessionFactory.openSession()) {
            Ticket ticket = null;
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                ticket = session.createQuery("from Ticket where id="+id, Ticket.class).getSingleResult();
                System.out.println("Ticket found: " + ticket);

                tx.commit();
                return ticket;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println("---------------------- ERROR ---------------------");
                System.out.println(ex);
            } catch (Exception exception) {
                logger.error(exception);
                System.out.println("Error DB " + exception);
            }
        }
        }finally {
            close();
            logger.traceExit();
        }
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        try{
            initialize();
        List<Ticket> tickets=new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                tickets = session.createQuery("from Ticket", Ticket.class).list();

                System.out.println("Tickets found: ");
                for (Ticket t : tickets){
                    System.out.println(t.getName() + ", " + t.getId());
                }
                tx.commit();
                return tickets;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();

            } catch (Exception exception) {
                logger.error(exception);
                System.out.println("Error DB " + exception);
            }
        }
        }finally {
            close();
            logger.traceExit();
        }
        return null;
    }

    @Override
    public Ticket findOneLogin(String email, String password) {
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
