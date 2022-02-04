package cz.cvut.fel.ear.libraria.rest;

import cz.cvut.fel.ear.libraria.exception.NotFoundException;
import cz.cvut.fel.ear.libraria.exception.ValidationException;
import cz.cvut.fel.ear.libraria.model.Copy;
import cz.cvut.fel.ear.libraria.rest.util.RestUtils;
import cz.cvut.fel.ear.libraria.service.BookService;
import cz.cvut.fel.ear.libraria.service.CopyService;
import cz.cvut.fel.ear.libraria.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copy")
public class CopyController {

    private static final Logger LOG = LoggerFactory.getLogger(CopyController.class);

    private final CopyService copyService;
    private final LocationService locationService;
    private final BookService bookService;

    @Autowired
    public CopyController(CopyService copyService, LocationService locationService, BookService bookService) {
        this.copyService = copyService;
        this.locationService = locationService;
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCopy(@RequestBody Copy copy) {
        if(copy.getLocation() == null) {
            copy.setLocation(locationService.find(copy.getLocationId()));
        }
        if(copy.getBook() == null) {
            copy.setBook(bookService.find(copy.getBookId()));
        }
        copyService.persist(copy);
        LOG.debug("Created copy {}.", copy);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", copy.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Copy> getCopies() {
        return copyService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Copy getCopies(@PathVariable("id") Integer id) {
        final Copy copy = copyService.find(id);
        if (copy == null) {
            throw NotFoundException.create("Copy identified by", id);
        }
        return copy;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCopy(@PathVariable("id") Integer id, @RequestBody Copy copy) {
        final Copy original = getCopies(id);
        if (!original.getId().equals(copy.getId())) {
            throw new ValidationException("Product identifier in the data does not match the one in the request URL.");
        }
        copyService.update(copy);
        LOG.debug("Updated copy {}.", copy);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCopy(@PathVariable("id") Integer id) {
        final Copy copy = copyService.find(id);
        if (copy != null) {
            copyService.remove(copy);
            LOG.debug("Copy {} removed.", copy);
        }
    }
}
