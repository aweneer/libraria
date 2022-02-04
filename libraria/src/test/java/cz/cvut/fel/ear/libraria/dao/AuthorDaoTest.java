package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Author;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthorDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AuthorDao dao;

    private Author author;

    @Before
    public void setUp() {
        //Author
        author = Generator.generateAuthor();
        em.persist(author);
    }

    @Test
    public void findByFirstNameReturnsPersonWithMatchingFirstName() {
        final List<Author> results = dao.findByFirstName(author.getFirstName());
        assertNotNull(results);

        Author result = null;
        for(Author resultElement : results) {
            if(author.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByFirstNameReturnsEmptyListForUnknownFirstName() {
        final List<Author> results = dao.findByFirstName("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByLastNameReturnsPersonWithMatchingLastName() {
        final List<Author> results = dao.findByLastName(author.getLastName());
        assertNotNull(results);

        Author result = null;
        for(Author resultElement : results) {
            if(author.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByLastNameReturnsEmptyListForUnknownLastName() {
        final List<Author> results = dao.findByLastName("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByBiographyReturnsPersonWithMatchingBiography() {
        final List<Author> results = dao.findByBiography(author.getBiography());
        assertNotNull(results);

        Author result = null;
        for(Author resultElement : results) {
            if(author.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByBiographyReturnsEmptyListForUnknownBiography() {
        final List<Author> results = dao.findByBiography("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
