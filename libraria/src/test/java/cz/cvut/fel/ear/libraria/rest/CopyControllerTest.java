package cz.cvut.fel.ear.libraria.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Copy;
import cz.cvut.fel.ear.libraria.rest.handler.ErrorInfo;
import cz.cvut.fel.ear.libraria.service.CopyService;
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

public class CopyControllerTest extends BaseControllerTestRunner {

    @Mock
    private CopyService copyServiceMock;

    @InjectMocks
    private CopyController sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsAllCopies() throws Exception {
        final List<Copy> copies = IntStream.range(0, 5).mapToObj(i -> Generator.generateCopy()).collect(
                Collectors.toList());;
        when(copyServiceMock.findAll()).thenReturn(copies);
        final MvcResult mvcResult = mockMvc.perform(get("/copy")).andReturn();
        final List<Copy> result = readValue(mvcResult, new TypeReference<List<Copy>>() {
        });
        assertNotNull(result);
        assertEquals(copies.size(), result.size());
        for (int i = 0; i < copies.size(); i++) {
            assertEquals(copies.get(i).getId(), result.get(i).getId());
            assertEquals(copies.get(i).getSignature(), result.get(i).getSignature());
            assertEquals(copies.get(i).getPublisher(), result.get(i).getPublisher());
            assertEquals(copies.get(i).getIsbn(), result.get(i).getIsbn());
        }
    }

    @Test
    public void getByIdReturnsProductWithMatchingId() throws Exception {
        final Copy copy = Generator.generateCopy();
        copy.setId(123);
        when(copyServiceMock.find(copy.getId())).thenReturn(copy);
        final MvcResult mvcResult = mockMvc.perform(get("/copy/" + copy.getId())).andReturn();
        final Copy result = readValue(mvcResult, Copy.class);
        assertNotNull(result);
        assertEquals(copy.getId(), result.getId());
        assertEquals(copy.getSignature(), result.getSignature());
        assertEquals(copy.getPublisher(), result.getPublisher());
        assertEquals(copy.getIsbn(), result.getIsbn());
    }

    @Test
    public void getByIdThrowsNotFoundForUnknownId() throws Exception {
        final int id = 123;
        final MvcResult mvcResult = mockMvc.perform(get("/copy/" + id)).andExpect(status().isNotFound())
                .andReturn();
        final ErrorInfo result = readValue(mvcResult, ErrorInfo.class);
        assertNotNull(result);
        assertThat(result.getMessage(), containsString("Copy identified by "));
        assertThat(result.getMessage(), containsString(Integer.toString(id)));
    }

    @Test
    public void removeRemovesProductUsingService() throws Exception {
        final Copy copy = Generator.generateCopy();
        copy.setId(123);
        when(copyServiceMock.find(copy.getId())).thenReturn(copy);
        mockMvc.perform(delete("/copy/" + copy.getId())).andExpect(status().isNoContent());
        verify(copyServiceMock).remove(copy);
    }

    @Test
    public void removeDoesNothingWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(delete("/copy/123")).andExpect(status().isNoContent());
        verify(copyServiceMock, never()).remove(any());
    }
}
