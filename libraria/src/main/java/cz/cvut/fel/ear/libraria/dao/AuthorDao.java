package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class AuthorDao extends BaseDao<Author> {

    public AuthorDao() {super(Author.class);}

    public List<Author> findByFirstName(String firstName) {
        try {
            return em.createNamedQuery("Author.findByFirstName", Author.class).setParameter("firstName", firstName).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Author> findByLastName(String lastName) {
        try {
            return em.createNamedQuery("Author.findByLastName", Author.class).setParameter("lastName", lastName).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Author> findByBiography(String biography) {
        try {
            return em.createNamedQuery("Author.findByBiography", Author.class).setParameter("biography", biography).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
