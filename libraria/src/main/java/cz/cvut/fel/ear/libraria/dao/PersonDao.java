package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

@Repository
public class PersonDao extends BaseDao<Person> {

    public PersonDao() {super(Person.class);}

    public List<Person> findByUsername(String username) {
        try {
            return em.createNamedQuery("Person.findByUsername", Person.class).setParameter("username", username).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Person> findByPassword(String password) {
        try {
            return em.createNamedQuery("Person.findByPassword", Person.class).setParameter("password", password).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Person> findByRole(String role) {
        try {
            return em.createNamedQuery("Person.findByRole", Person.class).setParameter("role", role).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Person> findByDateOfBirth(Date dateOfBirth) {
        try {
            return em.createNamedQuery("Person.findByDateOfBirth", Person.class).setParameter("dateOfBirth", dateOfBirth).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Person> findByEmail(String email) {
        try {
            return em.createNamedQuery("Person.findByEmail", Person.class).setParameter("email", email).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Person> findByPhoneNumber(String phoneNumber) {
        try {
            return em.createNamedQuery("Person.findByPhoneNumber", Person.class).setParameter("phoneNumber", phoneNumber).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
