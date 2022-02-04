package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Written;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class WrittenDao extends BaseDao<Written> {

    public WrittenDao() {super(Written.class);}

    public List<Written> findByBookId(int bookId) {
        try {
            return em.createNamedQuery("Written.findByBookId", Written.class).setParameter("bookId", bookId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Written> findByAuthorId(int authorId) {
        try {
            return em.createNamedQuery("Written.findByAuthorId", Written.class).setParameter("authorId", authorId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
