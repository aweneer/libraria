package cz.cvut.fel.ear.libraria.rest;

import cz.cvut.fel.ear.libraria.exception.NotFoundException;
import cz.cvut.fel.ear.libraria.exception.ValidationException;
import cz.cvut.fel.ear.libraria.model.Borrowing;
import cz.cvut.fel.ear.libraria.rest.util.RestUtils;
import cz.cvut.fel.ear.libraria.service.BorrowingService;
import cz.cvut.fel.ear.libraria.service.CopyService;
import cz.cvut.fel.ear.libraria.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {

    private static final Logger LOG = LoggerFactory.getLogger(BorrowingController.class);

    private final BorrowingService borrowingService;
    private final CopyService copyService;
    private final PersonService personService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService, CopyService copyService, PersonService personService) {
        this.borrowingService = borrowingService;
        this.copyService = copyService;
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBorrowing(@RequestBody Borrowing borrowing) {
        if(borrowing.getCopy() == null) {
            borrowing.setCopy(copyService.find(borrowing.getCopyId()));
        }
        if(borrowing.getPerson() == null) {
            borrowing.setPerson(personService.find(borrowing.getPersonId()));
        }
        borrowingService.persist(borrowing);
        LOG.debug("Created borrowing {}.", borrowing);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", borrowing.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Borrowing> getBorrowings() {
        return borrowingService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Borrowing getBorrowings(@PathVariable("id") Integer id) {
        final Borrowing borrowing = borrowingService.find(id);
        if (borrowing == null) {
            throw NotFoundException.create("Borrowing identified by", id);
        }
        return borrowing;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBorrowing(@PathVariable("id") Integer id, @RequestBody Borrowing borrowing) {
        final Borrowing original = getBorrowings(id);
        if (!original.getId().equals(borrowing.getId())) {
            throw new ValidationException("Product identifier in the data does not match the one in the request URL.");
        }
        borrowingService.update(borrowing);
        LOG.debug("Updated borrowing {}.", borrowing);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBorrowing(@PathVariable("id") Integer id) {
        final Borrowing product = borrowingService.find(id);
        if (product != null) {
            borrowingService.remove(product);
            LOG.debug("Borrowing {} removed.", product);
        }
    }
}
