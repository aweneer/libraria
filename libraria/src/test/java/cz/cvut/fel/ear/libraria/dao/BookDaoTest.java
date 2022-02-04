package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Category;
import cz.cvut.fel.ear.libraria.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BookDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookDao dao;

    private Category category;
    private Book book;

    @Before
    public void setUp() {
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
    }

    @Test
    public void findByCategoryIdReturnsPersonWithMatchingCategoryId() {
        final List<Book> results = dao.findByCategoryId(book.getCategory().getId());
        assertNotNull(results);

        Book result = null;
        for(Book resultElement : results) {
            if(book.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByCategoryIdReturnsEmptyListForUnknownCategoryId() {
        final List<Book> results = dao.findByCategoryId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByNameReturnsPersonWithMatchingName() {
        final List<Book> results = dao.findByName(book.getName());
        assertNotNull(results);

        Book result = null;
        for(Book resultElement : results) {
            if(book.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByNameReturnsEmptyListForUnknownName() {
        final List<Book> results = dao.findByName("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByLanguageReturnsPersonWithMatchingLanguage() {
        final List<Book> results = dao.findByLanguage(book.getLanguage());
        assertNotNull(results);

        Book result = null;
        for(Book resultElement : results) {
            if(book.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByLanguageReturnsEmptyListForUnknownLanguage() {
        final List<Book> results = dao.findByLanguage("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
