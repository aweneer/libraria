package cz.cvut.fel.ear.libraria.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Book;
import cz.cvut.fel.ear.libraria.rest.handler.ErrorInfo;
import cz.cvut.fel.ear.libraria.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTestRunner {

    @Mock
    private BookService bookServiceMock;

    @InjectMocks
    private BookController sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsAllBooks() throws Exception {
        final List<Book> books = IntStream.range(0, 5).mapToObj(i -> Generator.generateBook()).collect(
                Collectors.toList());;
        when(bookServiceMock.findAll()).thenReturn(books);
        final MvcResult mvcResult = mockMvc.perform(get("/book")).andReturn();
        final List<Book> result = readValue(mvcResult, new TypeReference<List<Book>>() {
        });
        assertNotNull(result);
        assertEquals(books.size(), result.size());
        for (int i = 0; i < books.size(); i++) {
            assertEquals(books.get(i).getId(), result.get(i).getId());
            assertEquals(books.get(i).getName(), result.get(i).getName());
            assertEquals(books.get(i).getLanguage(), result.get(i).getLanguage());
        }
    }

    @Test
    public void getByIdReturnsProductWithMatchingId() throws Exception {
        final Book book = Generator.generateBook();
        book.setId(123);
        when(bookServiceMock.find(book.getId())).thenReturn(book);
        final MvcResult mvcResult = mockMvc.perform(get("/book/" + book.getId())).andReturn();
        final Book result = readValue(mvcResult, Book.class);
        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getName(), result.getName());
        assertEquals(book.getLanguage(), result.getLanguage());
    }

    @Test
    public void getByIdThrowsNotFoundForUnknownId() throws Exception {
        final int id = 123;
        final MvcResult mvcResult = mockMvc.perform(get("/book/" + id)).andExpect(status().isNotFound())
                .andReturn();
        final ErrorInfo result = readValue(mvcResult, ErrorInfo.class);
        assertNotNull(result);
        assertThat(result.getMessage(), containsString("Book identified by "));
        assertThat(result.getMessage(), containsString(Integer.toString(id)));
    }

    @Test
    public void removeRemovesProductUsingService() throws Exception {
        final Book book = Generator.generateBook();
        book.setId(123);
        when(bookServiceMock.find(book.getId())).thenReturn(book);
        mockMvc.perform(delete("/book/" + book.getId())).andExpect(status().isNoContent());
        verify(bookServiceMock).remove(book);
    }

    @Test
    public void removeDoesNothingWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(delete("/book/123")).andExpect(status().isNoContent());
        verify(bookServiceMock, never()).remove(any());
    }
}
