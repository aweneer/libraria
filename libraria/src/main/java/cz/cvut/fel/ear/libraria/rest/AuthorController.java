package cz.cvut.fel.ear.libraria.rest;

import cz.cvut.fel.ear.libraria.exception.NotFoundException;
import cz.cvut.fel.ear.libraria.exception.ValidationException;
import cz.cvut.fel.ear.libraria.model.Author;
import cz.cvut.fel.ear.libraria.rest.util.RestUtils;
import cz.cvut.fel.ear.libraria.service.AuthorService;
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
@RequestMapping("/author")
public class AuthorController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAuthor(@RequestBody Author author) {
        authorService.persist(author);
        LOG.debug("Created author {}.", author);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", author.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Author> getAuthors() {
        return authorService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Author getAuthors(@PathVariable("id") Integer id) {
        final Author author = authorService.find(id);
        if (author == null) {
            throw NotFoundException.create("Author identified by", id);
        }
        return author;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthor(@PathVariable("id") Integer id, @RequestBody Author author) {
        final Author original = authorService.find(id);
        if (!original.getId().equals(author.getId())) {
            throw new ValidationException("Product identifier in the data does not match the one in the request URL.");
        }
        authorService.update(author);
        LOG.debug("Updated author {}.", author);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAuthor(@PathVariable("id") Integer id) {
        final Author author = authorService.find(id);
        if (author != null) {
            authorService.remove(author);
            LOG.debug("Author {} removed.", author);
        }
    }
}
