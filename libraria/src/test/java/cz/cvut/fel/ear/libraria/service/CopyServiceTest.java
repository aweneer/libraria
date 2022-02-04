package cz.cvut.fel.ear.libraria.service;

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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CopyServiceTest extends BaseServiceTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CopyService service;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    private Location location;
    private Category category;
    private Book book;
    private Copy copy;

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
        service.persist(copy);

        //Copies In Location
        location.addCopy(copy);
        locationService.persist(location);

        //Copies in Title
        book.addCopy(copy);
        bookService.persist(book);
    }

    @Test
    public void persistCreatesCopy() {
        final Copy result = em.find(Copy.class, copy.getId());
        assertNotNull(result);
    }

    @Test
    public void findAllReturnsNonEmptyList() {
        final List<Copy> result = service.findAll();
        assertFalse(result.isEmpty());
    }
}
