package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BorrowingDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BorrowingDao dao;

    private Location location;
    private Category category;
    private Book book;
    private Copy copy;
    private Person person;
    private Borrowing borrowing;

    @Before
    public void setUp() {
        //Location
        location = Generator.generateLocation();
        em.persist(location);

        //Category
        Category category = Generator.generateCategory();
        em.persist(category);

        //Book
        Book book = Generator.generateBook();
        book.setCategory(category);
        em.persist(book);

        //Books in Category
        category.addBook(book);
        em.persist(category);

        em.flush();

        //Copy
        Copy copy = Generator.generateCopy();
        copy.setLocation(location);
        copy.setBook(book);
        em.persist(copy);

        //Copies In Location
        location.addCopy(copy);
        em.persist(location);

        //Copies in Book
        book.addCopy(copy);
        em.persist(book);

        //Person
        person = Generator.generatePerson();
        em.persist(person);

        em.flush();

        //Borrowing
        borrowing = Generator.generateBorrowing();
        borrowing.setCopy(copy);
        borrowing.setPerson(person);
        em.persist(borrowing);

        em.flush();

        //Borrowings In Copy
        copy.addBorrowing(borrowing);
        em.persist(copy);

        //Borrowings in Person
        person.addBorrowing(borrowing);
        em.persist(person);
    }

    @Test
    public void findByCopyIdReturnsPersonWithMatchingCopyId() {
        final List<Borrowing> results = dao.findByCopyId(borrowing.getCopy().getId());
        assertNotNull(results);

        Borrowing result = null;
        for(Borrowing resultElement : results) {
            if(borrowing.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByCopyIdReturnsEmptyListForUnknownCopyId() {
        final List<Borrowing> results = dao.findByCopyId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByPersonIdReturnsPersonWithMatchingPersonId() {
        final List<Borrowing> results = dao.findByPersonId(borrowing.getPerson().getId());
        assertNotNull(results);

        Borrowing result = null;
        for(Borrowing resultElement : results) {
            if(borrowing.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByPersonIdReturnsEmptyListForUnknownPersonId() {
        final List<Borrowing> results = dao.findByPersonId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByStartingDateReturnsPersonWithMatchingStartingDate() {
        final List<Borrowing> results = dao.findByStartingDate(borrowing.getStartingDate());
        assertNotNull(results);

        Borrowing result = null;
        for(Borrowing resultElement : results) {
            if(borrowing.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByStartingDateReturnsEmptyListForUnknownStartingDate() {
        final List<Borrowing> results = dao.findByStartingDate(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByEndingDateReturnsPersonWithMatchingEndingDate() {
        final List<Borrowing> results = dao.findByEndingDate(borrowing.getEndingDate());
        assertNotNull(results);

        Borrowing result = null;
        for(Borrowing resultElement : results) {
            if(borrowing.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByEndingDateReturnsEmptyListForUnknown () {
        final List<Borrowing> results = dao.findByEndingDate(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
