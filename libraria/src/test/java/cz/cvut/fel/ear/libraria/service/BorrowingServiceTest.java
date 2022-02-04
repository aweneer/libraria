package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class BorrowingServiceTest extends BaseServiceTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BorrowingService service;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private PersonService personService;

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
        locationService.persist(location);

        //Category
        category = Generator.generateCategory();
        categoryService.persist(category);

        //Title
        book = Generator.generateBook();
        book.setCategory(category);
        bookService.persist(book);

        //Titles in Category
        category.addBook(book);
        categoryService.persist(category);

        em.flush();

        //Copy
        copy = Generator.generateCopy();
        copy.setBook(book);
        copy.setLocation(location);
        copyService.persist(copy);

        //Copies In Location
        location.addCopy(copy);
        locationService.persist(location);

        //Copies in Title
        book.addCopy(copy);
        bookService.persist(book);

        //Person
        person = Generator.generatePerson();
        personService.persist(person);

        em.flush();

        //Borrowing
        service.createBorrowing(person, copy, new Date(LocalDate.now().toEpochDay()), Generator.randomDate());
        borrowing = service.findByPersonAndCopy(person, copy);
    }

    @Test
    public void persistCreatesBorrowing() {
        final Borrowing result = em.find(Borrowing.class, borrowing.getId());
        assertNotNull(result);
    }

    @Test
    public void findAllReturnsNonEmptyList() {
        final List<Borrowing> result = service.findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    public void updateEndingDateChangesTheEndingDate() {
        final Date date1970 = new Date(LocalDate.now().toEpochDay());
        final Borrowing result = em.find(Borrowing.class, borrowing.getId());

        service.updateEndingDateBorrowing(borrowing, date1970);
        assertEquals(result.getEndingDate(), date1970);
    }
}
