package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Author;
import cz.cvut.fel.ear.libraria.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDao extends BaseDao<Book> {

    public BookDao() {super(Book.class);}

    public List<Book> findByCategoryId(int categoryId) {
        try {
            return em.createNamedQuery("Book.findByCategoryId", Book.class).setParameter("categoryId", categoryId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Book> findByName(String name) {
        try {
            return em.createNamedQuery("Book.findByName", Book.class).setParameter("name", name).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Book> findByLanguage(String language) {
        try {
            return em.createNamedQuery("Book.findByLanguage", Book.class).setParameter("language", language).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Book> findByAuthor(Author author) {
        List<Book> books = new ArrayList<>();

        for(Book book : findAll()) {
            if(book.getAuthors().contains(author)) books.add(book);
        }

        return books;
    }

}
