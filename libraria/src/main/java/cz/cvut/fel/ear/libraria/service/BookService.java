package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.BookDao;
import cz.cvut.fel.ear.libraria.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookDao dao;

    @Autowired
    public BookService(BookDao dao) {
        this.dao = dao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Book book) {
        Objects.requireNonNull(book);
        dao.persist(book);
    }

    @Transactional(readOnly = true)
    public Book find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> findAllByCategoryId(Integer id) {
        return dao.findByCategoryId(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Book book) {
        dao.update(book);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Book book) {
        Objects.requireNonNull(book);
        dao.remove(book);
    }

}
