package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.WrittenDao;
import cz.cvut.fel.ear.libraria.model.Written;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class WrittenService {

    private final WrittenDao dao;

    @Autowired
    public WrittenService(WrittenDao dao) {
        this.dao = dao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Written written) {
        Objects.requireNonNull(written);
        dao.persist(written);
    }

    @Transactional(readOnly = true)
    @PostFilter("hasRole('ROLE_ADMIN')")
    public Written find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    @PostFilter("hasRole('ROLE_ADMIN')")
    public List<Written> findAll() {
        return dao.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Written written) {
        dao.update(written);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Written written) {
        Objects.requireNonNull(written);
        dao.remove(written);
    }
}
