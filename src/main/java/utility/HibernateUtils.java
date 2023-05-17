package utility;

import model.Event;
import model.File;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Event.class)
                    .addAnnotatedClass(File.class)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
