package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Copy;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

@Repository
public class CopyDao extends BaseDao<Copy> {

    public CopyDao() {super(Copy.class);}

    public List<Copy> findByLocationId(int locationId) {
        try {
            return em.createNamedQuery("Copy.findByLocationId", Copy.class).setParameter("locationId", locationId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Copy> findByBookId(int bookId) {
        try {
            return em.createNamedQuery("Copy.findByBookId", Copy.class).setParameter("bookId", bookId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Copy> findBySignature(String signature) {
        try {
            return em.createNamedQuery("Copy.findBySignature", Copy.class).setParameter("signature", signature).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Copy> findByPublisher(String publisher) {
        try {
            return em.createNamedQuery("Copy.findByPublisher", Copy.class).setParameter("publisher", publisher).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Copy> findByDateOfPublication(Date dateOfPublication) {
        try {
            return em.createNamedQuery("Copy.findByDateOfPublication", Copy.class).setParameter("dateOfPublication", dateOfPublication).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Copy> findByPagesCount(int pagesCount) {
        try {
            return em.createNamedQuery("Copy.findByPagesCount", Copy.class).setParameter("pagesCount", pagesCount).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }


    public List<Copy> findByIsbn(int isbn) {
        try {
            return em.createNamedQuery("Copy.findByIsbn", Copy.class).setParameter("isbn", isbn).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
