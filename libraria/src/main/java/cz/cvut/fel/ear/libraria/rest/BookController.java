package cz.cvut.fel.ear.libraria.rest;

import cz.cvut.fel.ear.libraria.exception.NotFoundException;
import cz.cvut.fel.ear.libraria.exception.ValidationException;
import cz.cvut.fel.ear.libraria.model.Book;
import cz.cvut.fel.ear.libraria.rest.util.RestUtils;
import cz.cvut.fel.ear.libraria.service.BookService;
import cz.cvut.fel.ear.libraria.service.CategoryService;
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
@RequestMapping("/book")
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final CategoryService categoryService;

    @Autowired
    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBook(@RequestBody Book book) {
        if(book.getCategory() == null) {
            book.setCategory(categoryService.find(book.getCategoryId()));
        }
        bookService.persist(book);
        LOG.debug("Created book {}.", book);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", book.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBooks(@PathVariable("id") Integer id) {
        final Book book = bookService.find(id);
        if (book == null) {
            throw NotFoundException.create("Book identified by", id);
        }
        return book;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("id") Integer id, @RequestBody Book book) {
        final Book original = getBooks(id);
        if (!original.getId().equals(book.getId())) {
            throw new ValidationException("Product identifier in the data does not match the one in the request URL.");
        }
        bookService.update(book);
        LOG.debug("Updated book {}.", book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBook(@PathVariable("id") Integer id) {
        final Book book = bookService.find(id);
        if (book != null) {
            bookService.remove(book);
            LOG.debug("Book {} removed.", book);
        }
    }
}
