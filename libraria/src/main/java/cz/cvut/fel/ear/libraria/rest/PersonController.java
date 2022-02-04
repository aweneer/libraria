package cz.cvut.fel.ear.libraria.rest;

import cz.cvut.fel.ear.libraria.exception.NotFoundException;
import cz.cvut.fel.ear.libraria.exception.ValidationException;
import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.rest.util.RestUtils;
import cz.cvut.fel.ear.libraria.service.PersonService;
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
@RequestMapping("/person")
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        personService.persist(person);
        LOG.debug("Created person {}.", person);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", person.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> getPeople() {
        return personService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPeople(@PathVariable("id") Integer id) {
        final Person person = personService.find(id);
        if (person == null) {
            throw NotFoundException.create("Person identified by", id);
        }
        return person;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(@PathVariable("id") Integer id, @RequestBody Person person) {
        final Person original = getPeople(id);
        if (!original.getId().equals(person.getId())) {
            throw new ValidationException("Product identifier in the data does not match the one in the request URL.");
        }
        personService.update(person);
        LOG.debug("Updated person {}.", person);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePerson(@PathVariable("id") Integer id) {
        final Person person = personService.find(id);
        if (person != null) {
            personService.remove(person);
            LOG.debug("Person {} removed.", person);
        }
    }
}
