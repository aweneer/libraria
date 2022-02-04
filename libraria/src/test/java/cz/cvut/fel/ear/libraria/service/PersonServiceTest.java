package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Copy;
import cz.cvut.fel.ear.libraria.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PersonServiceTest extends BaseServiceTestRunner {

    @PersistenceContext
    private EntityManager em;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonService service;

    private Person person;

    @Before
    public void setUp() {
        person = Generator.generatePerson();
        service.persist(person);
    }

    @Test
    public void persistCreatesPerson() {
        final Person result = em.find(Person.class, person.getId());
        assertNotNull(result);
    }

    @Test
    public void findAllReturnsNonEmptyList() {
        final List<Person> result = service.findAll();
        assertFalse(result.isEmpty());
    }
}
