package repository.hibernate;

import model.User;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import repository.UserRepository;
import utility.HibernateUtils;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    @Override
    public User save(User user) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return user;
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        String hql = "from User u left join fetch u.events";
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery(hql, User.class).getResultList();
        }
    }

    @Override
    public User getById(Integer integer) {
        User user;
        String hql = "from User u left join fetch u.events where u.id = :id";
        try (Session session = HibernateUtils.getSession()) {
            Query<User> query =  session.createQuery(hql, User.class);
            query.setParameter("id", integer);
            user = query.getSingleResultOrNull();
        }

        return user;
    }

    @Override
    public boolean deleteById(Integer integer) {
        String hql = "delete from User where id = :id";
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", integer);
            if(query.executeUpdate() > 0){
                return true;
            }
            session.getTransaction().commit();
        }

        return false;
    }
}
