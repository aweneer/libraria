package cz.cvut.fel.ear.libraria.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.rest.handler.ErrorInfo;
import cz.cvut.fel.ear.libraria.service.PersonService;
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

public class PersonControllerTest extends BaseControllerTestRunner {

    @Mock
    private PersonService personServiceMock;

    @InjectMocks
    private PersonController sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsAllPeople() throws Exception {
        final List<Person> people = IntStream.range(0, 5).mapToObj(i -> Generator.generatePerson()).collect(
                Collectors.toList());
        when(personServiceMock.findAll()).thenReturn(people);
        final MvcResult mvcResult = mockMvc.perform(get("/person")).andReturn();
        final List<Person> result = readValue(mvcResult, new TypeReference<List<Person>>() {
        });
        assertNotNull(result);
        assertEquals(people.size(), result.size());
        for (int i = 0; i < people.size(); i++) {
            assertEquals(people.get(i).getId(), result.get(i).getId());
            assertEquals(people.get(i).getUsername(), result.get(i).getUsername());
            assertEquals(people.get(i).getPassword(), result.get(i).getPassword());
        }
    }

    @Test
    public void getByIdReturnsProductWithMatchingId() throws Exception {
        final Person person = Generator.generatePerson();
        person.setId(123);
        when(personServiceMock.find(person.getId())).thenReturn(person);
        final MvcResult mvcResult = mockMvc.perform(get("/person/" + person.getId())).andReturn();
        final Person result = readValue(mvcResult, Person.class);
        assertNotNull(result);
        assertEquals(person.getId(), result.getId());
        assertEquals(person.getUsername(), result.getUsername());
        assertEquals(person.getPassword(), result.getPassword());
    }

    @Test
    public void getByIdThrowsNotFoundForUnknownId() throws Exception {
        final int id = 123;
        final MvcResult mvcResult = mockMvc.perform(get("/person/" + id)).andExpect(status().isNotFound())
                .andReturn();
        final ErrorInfo result = readValue(mvcResult, ErrorInfo.class);
        assertNotNull(result);
        assertThat(result.getMessage(), containsString("Person identified by "));
        assertThat(result.getMessage(), containsString(Integer.toString(id)));
    }

    @Test
    public void removeRemovesProductUsingService() throws Exception {
        final Person person = Generator.generatePerson();
        person.setId(123);
        when(personServiceMock.find(person.getId())).thenReturn(person);
        mockMvc.perform(delete("/person/" + person.getId())).andExpect(status().isNoContent());
        verify(personServiceMock).remove(person);
    }

    @Test
    public void removeDoesNothingWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(delete("/person/123")).andExpect(status().isNoContent());
        verify(personServiceMock, never()).remove(any());
    }
}
