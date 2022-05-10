package project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import project.model.User;
import project.repository.database.UserHibernateRepository;

import java.util.List;

public class UserMain {
    //INSERT
    void addMessage(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                User user = new User("User1","User1", "email1", "password1");
                session.save(user);
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
                User user = session.load( User.class, 1 );
                session.update(user);
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

                User user = session.createQuery("from User where email like 'email1' ", User.class)
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
    }

    //SELECT
    void getMessages(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> users =
                        session.createQuery("from User", User.class).
                                setFirstResult(10).setMaxResults(5).
                                list();
                System.out.println("Users found: ");
                for (User u : users) {
                    System.out.println(u.getFirstName() + ' ' + u.getLastName());
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

            UserHibernateRepository test = new UserHibernateRepository(sessionFactory);

            User user = new User("User1","User1", "email1", "password1");
            test.save(user);
            test.save(user);
            test.findAll();
            User user1 = new User("User2","User2", "email2", "password2");
            user1.setId(user.getId());
            test.update(user1);
            User user2 = new User("User3","User3", "email3", "password3");
            test.save(user2);
            test.findOne(user2.getId());
            test.delete(user2.getId());
            test.findAll();

            //UserMain test = new UserMain();
            //test.addMessage();
            //test.getMessages();
            //test.updateMessage();
            //test.deleteMessage();
            //test.getMessages();
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
            //sessionFactory = new Configuration().configure().buildSessionFactory();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            //throw new ExceptionInInitializerError(ex);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }
}
