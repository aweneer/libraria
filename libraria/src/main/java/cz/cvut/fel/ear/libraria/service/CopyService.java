package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.CopyDao;
import cz.cvut.fel.ear.libraria.model.Copy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CopyService {

    private final CopyDao dao;

    @Autowired
    public CopyService(CopyDao dao) {
        this.dao = dao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Copy copy) {
        Objects.requireNonNull(copy);
        dao.persist(copy);
    }

    @Transactional(readOnly = true)
    public Copy find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Copy> findAll() {
        return dao.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Copy copy) {
        dao.update(copy);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Copy copy) {
        Objects.requireNonNull(copy);
        dao.remove(copy);
    }

}
