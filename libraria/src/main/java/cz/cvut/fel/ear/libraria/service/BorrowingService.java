package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.BorrowingDao;
import cz.cvut.fel.ear.libraria.dao.CopyDao;
import cz.cvut.fel.ear.libraria.dao.PersonDao;
import cz.cvut.fel.ear.libraria.model.Borrowing;
import cz.cvut.fel.ear.libraria.model.Copy;
import cz.cvut.fel.ear.libraria.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
public class BorrowingService {

    private final BorrowingDao dao;
    private final PersonDao personDao;
    private final CopyDao copyDao;

    @Autowired
    public BorrowingService(BorrowingDao dao, PersonDao personDao, CopyDao copyDao) {
        this.dao = dao;
        this.personDao = personDao;
        this.copyDao = copyDao;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Borrowing borrowing) {
        Objects.requireNonNull(borrowing);
        dao.persist(borrowing);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createBorrowing(Person person, Copy copy, Date startDate, Date endDate) {
        Borrowing borrowing = new Borrowing();
        borrowing.setPerson(person);
        borrowing.setCopy(copy);
        borrowing.setStartingDate(startDate);
        borrowing.setEndingDate(endDate);
        dao.persist(borrowing);

        person.addBorrowing(borrowing);
        personDao.update(person);

        copy.addBorrowing(borrowing);
        copyDao.update(copy);

        // TODO: admin can create borrowing for reader
    }

    @Transactional(readOnly = true)
    public Borrowing find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    @PostFilter("hasRole('ROLE_ADMIN') or (filterObject.person.username == principal.username)")
    public List<Borrowing> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Borrowing findByPersonAndCopy(Person person, Copy copy) {
        for(Borrowing borrowingPerson : dao.findByPersonId(person.getId())) {
            for(Borrowing borrowingCopy : dao.findByCopyId(copy.getId())) {
                if(borrowingPerson.equals(borrowingCopy)) return borrowingPerson;
            }
        }
        return null;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Borrowing borrowing) {
        dao.update(borrowing);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEndingDateBorrowing(Borrowing borrowing, Date date) {
        borrowing.setEndingDate(date);
        dao.update(borrowing);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Borrowing borrowing) {
        Objects.requireNonNull(borrowing);
        dao.remove(borrowing);
    }

}
