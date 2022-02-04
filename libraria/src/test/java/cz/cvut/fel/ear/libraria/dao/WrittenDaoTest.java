package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Author;
import cz.cvut.fel.ear.libraria.model.Category;
import cz.cvut.fel.ear.libraria.model.Book;
import cz.cvut.fel.ear.libraria.model.Written;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WrittenDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private WrittenDao dao;

    private Author author;
    private Category category;
    private Book book;
    private Written written;

    @Before
    public void setUp() {
        //Author
        Author author = Generator.generateAuthor();
        em.persist(author);

        //Category
        category = Generator.generateCategory();
        em.persist(category);

        //Title
        book = Generator.generateBook();
        book.setCategory(category);

        //Authors in Title
        book.addAuthor(author);
        em.persist(book);

        //Titles in Author
        author.addBook(book);
        em.persist(author);

        //Titles in Category
        category.addBook(book);
        em.persist(category);

        em.flush();

        //Written
        written = Generator.generateWritten();
        written.setAuthorId(author.getId());
        written.setBookId(book.getId());
        em.persist(written);
    }

    @Test
    public void findByBookIdReturnsPersonWithMatchingBookId() {
        final List<Written> results = dao.findByBookId(written.getBookId());
        assertNotNull(results);

        Written result = null;
        for(Written resultElement : results) {
            if(written.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByBookIdReturnsEmptyListForUnknownBookId() {
        final List<Written> results = dao.findByBookId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByAuthorIdReturnsPersonWithMatchingAuthorId() {
        final List<Written> results = dao.findByAuthorId(written.getAuthorId());
        assertNotNull(results);

        Written result = null;
        for(Written resultElement : results) {
            if(written.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByAuthorIdReturnsEmptyListForUnknownAuthorId() {
        final List<Written> results = dao.findByAuthorId(-1);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
