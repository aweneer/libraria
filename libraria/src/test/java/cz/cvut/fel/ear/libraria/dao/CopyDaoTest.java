package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Category;
import cz.cvut.fel.ear.libraria.model.Copy;
import cz.cvut.fel.ear.libraria.model.Location;
import cz.cvut.fel.ear.libraria.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CopyDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CopyDao dao;

    private Location location;
    private Category category;
    private Book book;
    private Copy copy;

    @Before
    public void setUp() {
        //Location
        location = Generator.generateLocation();
        em.persist(location);

        //Category
        category = Generator.generateCategory();
        em.persist(category);

        //Book
        book = Generator.generateBook();
        book.setCategory(category);
        em.persist(book);

        //Books in Category
        category.addBook(book);
        em.persist(category);

        em.flush();

        //Copy
        copy = Generator.generateCopy();
        copy.setBook(book);
        copy.setLocation(location);
        em.persist(copy);

        //Copies In Location
        location.addCopy(copy);
        em.persist(location);

        //Copies in book
        book.addCopy(copy);
        em.persist(book);
    }

    @Test
    public void findByLocationIdReturnsPersonWithMatchingLocationId() {
        final List<Copy> results = dao.findByLocationId(copy.getLocation().getId());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByLocationIdReturnsEmptyListForUnknownLocationId() {
        final List<Copy> results = dao.findByLocationId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByBookIdReturnsPersonWithMatchingBookId() {
        final List<Copy> results = dao.findByBookId(copy.getBook().getId());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByBookIdReturnsEmptyListForUnknownBookId() {
        final List<Copy> results = dao.findByBookId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findBySignatureReturnsPersonWithMatchingSignature() {
        final List<Copy> results = dao.findBySignature(copy.getSignature());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findBySignatureReturnsEmptyListForUnknownSignature() {
        final List<Copy> results = dao.findBySignature("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByPublisherReturnsPersonWithMatchingPublisher() {
        final List<Copy> results = dao.findByPublisher(copy.getPublisher());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByPublisherReturnsEmptyListForUnknownPublisher() {
        final List<Copy> results = dao.findByPublisher("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByDateOfPublicationReturnsPersonWithMatchingDateOfPublication() {
        final List<Copy> results = dao.findByDateOfPublication(copy.getDateOfPublication());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByDateOfPublicationReturnsEmptyListForUnknownDateOfPublication() {
        final List<Copy> results = dao.findByDateOfPublication(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByPagesCountReturnsPersonWithMatchingPagesCount() {
        final List<Copy> results = dao.findByPagesCount(copy.getPagesCount());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByPagesCountReturnsEmptyListForUnknownPagesCount() {
        final List<Copy> results = dao.findByPagesCount(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByIsbnReturnsPersonWithMatchingIsbn() {
        final List<Copy> results = dao.findByIsbn(copy.getIsbn());
        assertNotNull(results);

        Copy result = null;
        for(Copy resultElement : results) {
            if(copy.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByIsbnReturnsEmptyListForUnknownIsbn() {
        final List<Copy> results = dao.findByIsbn(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
