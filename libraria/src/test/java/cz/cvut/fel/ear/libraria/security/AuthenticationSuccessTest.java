package cz.cvut.fel.ear.libraria.security;

import cz.cvut.fel.ear.libraria.dao.PersonDao;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.security.util.*;
import cz.cvut.fel.ear.libraria.service.*;
import cz.cvut.fel.ear.libraria.config.*;
import cz.cvut.fel.ear.libraria.environment.config.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.ear.libraria.security.util.LoginStatus;
import cz.cvut.fel.ear.libraria.service.BaseServiceTestRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {TestSecurityConfig.class, RestConfig.class})
public class AuthenticationSuccessTest extends BaseServiceTestRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthenticationProvider provider;

    @Autowired
    private AuthenticationSuccess success;

    @Autowired
    private ObjectMapper mapper;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PersonDao dao;

    @Test
    public void authenticationSuccessReturnsLoginStatusWithUserInfo() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        SecurityUtils.setCurrentUser(new PersonDetails(Generator.generateAdminPerson()));
        /*
        // TODO: missing Authentication implementation
        List<Person> persons = dao.findAll();
        Person person = persons.get(0);
        System.out.println(person.getUsername());
        System.out.println(person.getPassword());
        personService.persist(person);
        PersonDetails personDetails = new PersonDetails(person);

        final Authentication auth = new UsernamePasswordAuthenticationToken(personDetails, personDetails.getPassword());
        provider.authenticate(auth);
        success.onAuthenticationSuccess(request, response, auth);
        final LoginStatus status = mapper.readValue(response.getContentAsString(), LoginStatus.class);
        assertTrue(status.isSuccess());
        assertFalse(!status.isLoggedIn());
        assertEquals(status.getUsername(), person.getUsername());
        assertNull(status.getErrorMessage());
        */
    }
}
