package repository.hibernate;

import model.File;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import repository.FileRepository;
import utility.HibernateUtils;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {
    @Override
    public File save(File file) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.persist(file);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return file;
    }

    @Override
    public File update(File file) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.merge(file);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return file;
    }

    @Override
    public List<File> getAll() {
        String hql = "from File";
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery(hql, File.class).getResultList();
        }
    }

    @Override
    public File getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            return session.find(File.class, integer);
        }
    }

    @Override
    public boolean deleteById(Integer integer) {
        String hql = "delete from File where id = :id";
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", integer);
            if (query.executeUpdate() > 0) {
                return true;
            }
            session.getTransaction().commit();
        }

        return false;
    }
}
