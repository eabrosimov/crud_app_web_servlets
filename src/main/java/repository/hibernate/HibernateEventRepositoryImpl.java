package repository.hibernate;

import model.Event;
import org.hibernate.Session;
import org.hibernate.query.Query;
import repository.EventRepository;
import utility.HibernateUtils;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
    public Event save(Event event) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.persist(event);
            session.getTransaction().commit();
        }

        return event;
    }

    @Override
    public Event update(Event event) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.merge(event);
            session.getTransaction().commit();
        }

        return event;
    }

    @Override
    public List<Event> getAll() {
        String hql = "from Event";
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery(hql, Event.class).getResultList();
        }
    }

    @Override
    public Event getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            return session.find(Event.class, integer);
        }
    }

    @Override
    public boolean deleteById(Integer integer) {
        String hql = "delete from Event where id = :id";
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", integer);
            if (query.executeUpdate() > 0) {
                return true;
            }
        }

        return false;
    }
}
