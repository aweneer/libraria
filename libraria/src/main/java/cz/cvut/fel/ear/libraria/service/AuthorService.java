package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.AuthorDao;
import cz.cvut.fel.ear.libraria.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AuthorService {

    private final AuthorDao dao;

    @Autowired
    public AuthorService(AuthorDao dao) {
        this.dao = dao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Author author) {
        Objects.requireNonNull(author);
        dao.persist(author);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Author author) {
        dao.update(author);
    }

    @Transactional(readOnly = true)
    public Author find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return dao.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Author author) {
        Objects.requireNonNull(author);
        dao.remove(author);
    }
}
