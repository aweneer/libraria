package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.BorrowingDao;
import cz.cvut.fel.ear.libraria.dao.CopyDao;
import cz.cvut.fel.ear.libraria.dao.PersonDao;
import cz.cvut.fel.ear.libraria.model.Borrowing;
import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Objects;

@Service
public class PersonService {

    private final PersonDao dao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PersonService(PersonDao dao) {
        this.dao = dao;
    }

    @Transactional(transactionManager = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Person person) {
        Objects.requireNonNull(person);
        // TODO: encode password

        dao.persist(person);
    }

    @Transactional(readOnly = true)
    @PostFilter("hasRole('ROLE_ADMIN')")
    public Person find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    @PostFilter("hasRole('ROLE_ADMIN') or (filterObject.username == principal.username)")
    public List<Person> findAll() {
        return dao.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Person person) {
        dao.update(person);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Person person) {
        Objects.requireNonNull(person);
        dao.remove(person);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeById(int personId) {
        Person p = dao.find(personId);
        dao.remove(p);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }
}
