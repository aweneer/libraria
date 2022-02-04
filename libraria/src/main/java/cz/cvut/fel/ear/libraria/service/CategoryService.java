package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.CategoryDao;
import cz.cvut.fel.ear.libraria.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private final CategoryDao dao;

    @Autowired
    public CategoryService(CategoryDao dao) {
        this.dao = dao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Category category) {
        Objects.requireNonNull(category);
        dao.persist(category);
    }

    @Transactional(readOnly = true)
    public Category find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return dao.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Category category) {
        dao.update(category);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Category category) {
        Objects.requireNonNull(category);
        dao.remove(category);
    }

}
