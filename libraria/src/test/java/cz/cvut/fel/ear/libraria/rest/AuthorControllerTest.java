package cz.cvut.fel.ear.libraria.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Author;
import cz.cvut.fel.ear.libraria.rest.handler.ErrorInfo;
import cz.cvut.fel.ear.libraria.service.AuthorService;
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

public class AuthorControllerTest extends BaseControllerTestRunner {

    @Mock
    private AuthorService authorServiceMock;

    @InjectMocks
    private AuthorController sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsAllAuthors() throws Exception {
        final List<Author> authors = IntStream.range(0, 5).mapToObj(i -> Generator.generateAuthor()).collect(
                Collectors.toList());
        when(authorServiceMock.findAll()).thenReturn(authors);
        final MvcResult mvcResult = mockMvc.perform(get("/author")).andReturn();
        final List<Author> result = readValue(mvcResult, new TypeReference<List<Author>>() {
        });
        assertNotNull(result);
        assertEquals(authors.size(), result.size());
        for (int i = 0; i < authors.size(); i++) {
            assertEquals(authors.get(i).getId(), result.get(i).getId());
            assertEquals(authors.get(i).getFirstName(), result.get(i).getFirstName());
            assertEquals(authors.get(i).getLastName(), result.get(i).getLastName());
        }
    }

    @Test
    public void getByIdReturnsProductWithMatchingId() throws Exception {
        final Author author = Generator.generateAuthor();
        author.setId(123);
        when(authorServiceMock.find(author.getId())).thenReturn(author);
        final MvcResult mvcResult = mockMvc.perform(get("/author/" + author.getId())).andReturn();
        final Author result = readValue(mvcResult, Author.class);
        assertNotNull(result);
        assertEquals(author.getId(), result.getId());
        assertEquals(author.getFirstName(), result.getFirstName());
        assertEquals(author.getLastName(), result.getLastName());
    }

    @Test
    public void getByIdThrowsNotFoundForUnknownId() throws Exception {
        final int id = 123;
        final MvcResult mvcResult = mockMvc.perform(get("/author/" + id)).andExpect(status().isNotFound())
                .andReturn();
        final ErrorInfo result = readValue(mvcResult, ErrorInfo.class);
        assertNotNull(result);
        assertThat(result.getMessage(), containsString("Author identified by "));
        assertThat(result.getMessage(), containsString(Integer.toString(id)));
    }

    @Test
    public void removeRemovesProductUsingService() throws Exception {
        final Author author = Generator.generateAuthor();
        author.setId(123);
        when(authorServiceMock.find(author.getId())).thenReturn(author);
        mockMvc.perform(delete("/author/" + author.getId())).andExpect(status().isNoContent());
        verify(authorServiceMock).remove(author);
    }

    @Test
    public void removeDoesNothingWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(delete("/author/123")).andExpect(status().isNoContent());
        verify(authorServiceMock, never()).remove(any());
    }
}
