package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.model.Role;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PersonDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PersonDao dao;

    private Person person;

    @Before
    public void setUp() {
        //Person
        person = Generator.generatePerson();
        em.persist(person);
    }

    @Test
    public void findByUsernameReturnsPersonWithMatchingUsername() {
        final List<Person> results = dao.findByUsername(person.getUsername());
        assertNotNull(results);

        Person result = null;
        for(Person resultElement : results) {
            if(person.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByUsernameReturnsEmptyListForUnknownUsername() {
        final List<Person> results = dao.findByUsername("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByPasswordReturnsPersonWithMatchingPassword() {
        final List<Person> results = dao.findByPassword(person.getPassword());
        assertNotNull(results);

        Person result = null;
        for(Person resultElement : results) {
            if(person.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByPasswordReturnsEmptyListForUnknownPassword() {
        final List<Person> results = dao.findByPassword("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByAccessLevelReturnsPersonWithMatchingAccessLevel() {
        final List<Person> results = dao.findByRole(person.getRole());
        assertNotNull(results);

        Person result = null;
        for(Person resultElement : results) {
            if(person.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByAccessLevelReturnsEmptyListForUnknownAccessLevel() {
        final List<Person> results = dao.findByRole(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByDateOfBirthReturnsPersonWithMatchingDateOfBirth() {
        final List<Person> results = dao.findByDateOfBirth(person.getDateOfBirth());
        assertNotNull(results);

        Person result = null;
        for(Person resultElement : results) {
            if(person.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByDateOfBirthReturnsEmptyListForUnknownDateOfBirth() {
        final List<Person> results = dao.findByDateOfBirth(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByEmailReturnsPersonWithMatchingEmail() {
        final List<Person> results = dao.findByEmail(person.getEmail());
        assertNotNull(results);

        Person result = null;
        for(Person resultElement : results) {
            if(person.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByEmailReturnsEmptyListForUnknownEmail() {
        final List<Person> results = dao.findByEmail("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
    @Test
    public void findByPhoneNumberReturnsPersonWithMatchingPhoneNumber() {
        final List<Person> results = dao.findByPhoneNumber(person.getPhoneNumber());
        assertNotNull(results);

        Person result = null;
        for(Person resultElement : results) {
            if(person.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByPhoneNumberReturnsEmptyListForUnknownPhoneNumber() {
        final List<Person> results = dao.findByPhoneNumber("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

}
