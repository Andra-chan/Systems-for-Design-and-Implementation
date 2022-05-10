
package project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import project.model.Ticket;

import java.util.List;

public class TicketMain {
    //INSERT
    void addMessage(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Ticket ticket = new Ticket(1, "Ticket1","Tourists1", "Address1", 1);
                session.save(ticket);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    void updateMessage(){
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Ticket artist = session.load( Ticket.class, 1 );
                session.update(artist);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    //DELETE
    void deleteMessage(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Ticket ticket = session.createQuery("from Tickets where id like '1' ", Ticket.class)
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Stergem mesajul " + ticket.getId());
                session.delete(ticket);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    //SELECT
    void getMessages(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Ticket> messages =
                        session.createQuery("from Tickets as t order by t.name asc", Ticket.class).
                                setFirstResult(10).setMaxResults(5).
                                list();
                System.out.println(messages.size() + " Ticket(s) found:");
                for (Ticket m : messages) {
                    System.out.println(m.getName() + ' ' + m.getId());
                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

    }

    public static void main(String[] args) {
        try {
            initialize();

            TicketMain test = new TicketMain();
            test.addMessage();
            //  test.getMessages();
            //  test.updateMessage();
            // test.deleteMessage();
            // test.getMessages();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
        }finally {
            close();
        }
    }


    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
}

