package cz.cvut.fel.ear.libraria.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Borrowing;
import cz.cvut.fel.ear.libraria.rest.handler.ErrorInfo;
import cz.cvut.fel.ear.libraria.service.BorrowingService;
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

public class BorrowingControllerTest extends BaseControllerTestRunner {

    @Mock
    private BorrowingService borrowingServiceMock;

    @InjectMocks
    private BorrowingController sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        super.setUp(sut);
    }

    @Test
    public void getAllReturnsAllBorrowings() throws Exception {
        final List<Borrowing> borrowings = IntStream.range(0, 5).mapToObj(i -> Generator.generateBorrowing()).collect(
                Collectors.toList());
        when(borrowingServiceMock.findAll()).thenReturn(borrowings);
        final MvcResult mvcResult = mockMvc.perform(get("/borrowing")).andReturn();
        final List<Borrowing> result = readValue(mvcResult, new TypeReference<List<Borrowing>>() {
        });
        assertNotNull(result);
        assertEquals(borrowings.size(), result.size());
        for (int i = 0; i < borrowings.size(); i++) {
            assertEquals(borrowings.get(i).getId(), result.get(i).getId());
        }
    }

    @Test
    public void getByIdReturnsProductWithMatchingId() throws Exception {
        final Borrowing product = Generator.generateBorrowing();
        product.setId(123);
        when(borrowingServiceMock.find(product.getId())).thenReturn(product);
        final MvcResult mvcResult = mockMvc.perform(get("/borrowing/" + product.getId())).andReturn();
        final Borrowing result = readValue(mvcResult, Borrowing.class);
        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void getByIdThrowsNotFoundForUnknownId() throws Exception {
        final int id = 123;
        final MvcResult mvcResult = mockMvc.perform(get("/borrowing/" + id)).andExpect(status().isNotFound())
                .andReturn();
        final ErrorInfo result = readValue(mvcResult, ErrorInfo.class);
        assertNotNull(result);
        assertThat(result.getMessage(), containsString("Borrowing identified by "));
        assertThat(result.getMessage(), containsString(Integer.toString(id)));
    }

    @Test
    public void removeRemovesProductUsingService() throws Exception {
        final Borrowing borrowing = Generator.generateBorrowing();
        borrowing.setId(123);
        when(borrowingServiceMock.find(borrowing.getId())).thenReturn(borrowing);
        mockMvc.perform(delete("/borrowing/" + borrowing.getId())).andExpect(status().isNoContent());
        verify(borrowingServiceMock).remove(borrowing);
    }

    @Test
    public void removeDoesNothingWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(delete("/borrowing/123")).andExpect(status().isNoContent());
        verify(borrowingServiceMock, never()).remove(any());
    }
}
