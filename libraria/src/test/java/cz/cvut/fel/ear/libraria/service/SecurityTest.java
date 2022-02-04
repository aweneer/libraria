package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.config.RestConfig;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.environment.config.TestSecurityConfig;
import cz.cvut.fel.ear.libraria.model.*;
import cz.cvut.fel.ear.libraria.security.util.PersonDetails;
import cz.cvut.fel.ear.libraria.security.util.SecurityUtils;

import cz.cvut.fel.ear.libraria.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {TestSecurityConfig.class, RestConfig.class})
public class SecurityTest extends BaseServiceTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BorrowingService borrowingService;

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

    @Before
    public void setUp() {

        SecurityUtils.setCurrentUser(new PersonDetails(Generator.generateAdminPerson()));

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


        em.flush();

        //Borrowing
        //borrowingService.createBorrowing(person, copy, new Date(LocalDate.now().toEpochDay()), Generator.randomDate());
        //borrowing = borrowingService.findByPersonAndCopy(person, copy);
    }


    private List<Borrowing> generateBorrowings(Person user) {
        final Person otherUser = Generator.generatePerson();
        otherUser.setRole(Role.ROLE_USER.getRole());
        em.persist(otherUser);
        final List<Borrowing> borrowings = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final Borrowing borrowing = new Borrowing();
            borrowing.setStartingDate(Date.valueOf(LocalDate.now()));
            borrowing.setEndingDate(Date.valueOf(LocalDate.now()));
            borrowing.setPerson(Generator.randomBoolean() ? user : otherUser);
            borrowing.setCopy(copy);
            borrowing.setCopyId(copy.getId());
            borrowing.setPersonId(borrowing.getPerson().getId());
            em.persist(borrowing);
            borrowings.add(borrowing);
        }
        return borrowings;
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteUserThrowsUnauthorizedExceptionForNonAdmin() {
        final Person data = Generator.generatePerson();
        data.setRole(Role.ROLE_USER.getRole());
        em.persist(data);
        final Person user = Generator.generatePerson();
        user.setRole(Role.ROLE_USER.getRole());
        SecurityUtils.setCurrentUser(new PersonDetails(user));

        try {
            personService.removeById(data.getId());
        } finally {
            assertNotNull(em.find(Person.class, data.getId()));
        }
    }

    @Test
    public void deleteUserWorksForAdminUser() {
        final Person data = Generator.generatePerson();
        data.setRole(Role.ROLE_USER.getRole());
        em.persist(data);
        final Person user = Generator.generatePerson();
        user.setRole(Role.ROLE_ADMIN.getRole());
        SecurityUtils.setCurrentUser(new PersonDetails(user));

        personService.removeById(data.getId());
        assertNull(em.find(Person.class, data.getId()));
    }

    @Test
    public void findAllReturnsBorrowingsOfUserForRegularUser() {
        final Person person = Generator.generatePerson();
        person.setRole(Role.ROLE_USER.getRole());
        em.persist(person);
        SecurityUtils.setCurrentUser(new PersonDetails(person));

        final List<Borrowing> allBorrowings = generateBorrowings(person);
        final List<Borrowing> result = borrowingService.findAll();
        final List<Borrowing> expected  = allBorrowings.stream().filter(b -> b.getPerson().getId().equals(person.getId())).collect(
                Collectors.toList());
        assertEquals(expected.size(), result.size());
        for (Borrowing b : result) {
            assertTrue(expected.stream().anyMatch(exp -> exp.getId().equals(b.getId())));
        }
    }

    @Test
    public void findAllReturnsAllBorrowingsForAdminUser() {
        final Person person = Generator.generatePerson();
        person.setRole(Role.ROLE_ADMIN.getRole());
        em.persist(person);
        SecurityUtils.setCurrentUser(new PersonDetails(person));

        final List<Borrowing> allBorrowings = generateBorrowings(person);
        final List<Borrowing> result = borrowingService.findAll();
        assertEquals(allBorrowings.size(), result.size());
    }
}
