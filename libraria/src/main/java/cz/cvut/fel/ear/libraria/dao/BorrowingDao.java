package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Borrowing;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

@Repository
public class BorrowingDao extends BaseDao<Borrowing> {

    public BorrowingDao() {super(Borrowing.class);}

    public List<Borrowing> findByCopyId(int copyId) {
        try {
            return em.createNamedQuery("Borrowing.findByCopyId", Borrowing.class).setParameter("copyId", copyId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Borrowing> findByPersonId(int personId) {
        try {
            return em.createNamedQuery("Borrowing.findByPersonId", Borrowing.class).setParameter("personId", personId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Borrowing> findByStartingDate(Date startingDate) {
        try {
            return em.createNamedQuery("Borrowing.findByStartingDate", Borrowing.class).setParameter("startingDate", startingDate).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Borrowing> findByEndingDate(Date endingDate) {
        try {
            return em.createNamedQuery("Borrowing.findByEndingDate", Borrowing.class).setParameter("endingDate", endingDate).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
