package cz.cvut.fel.ear.libraria.security;

import cz.cvut.fel.ear.libraria.config.RestConfig;
import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.environment.config.TestSecurityConfig;
import cz.cvut.fel.ear.libraria.model.Person;
import cz.cvut.fel.ear.libraria.security.util.PersonDetails;
import cz.cvut.fel.ear.libraria.security.util.SecurityUtils;
import cz.cvut.fel.ear.libraria.service.BaseServiceTestRunner;
import cz.cvut.fel.ear.libraria.service.PersonService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = {TestSecurityConfig.class, RestConfig.class})
public class DefaultAuthenticationProviderTest extends BaseServiceTestRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private DefaultAuthenticationProvider provider;

    private final Person person = Generator.generatePerson();
    private final String rawPassword = person.getPassword();

    @Before
    public void setUp() {
        SecurityUtils.setCurrentUser(new PersonDetails(Generator.generateAdminPerson()));
        personService.persist(person);
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }

    @After
    public void tearDown() {
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }


    @Test
    @Ignore
    public void successfulAuthenticationSetsSecurityContext() {
        final Authentication auth = new UsernamePasswordAuthenticationToken(person.getUsername(), rawPassword);
        final SecurityContext context = SecurityContextHolder.getContext();
        assertNull(context.getAuthentication());
        final Authentication result = provider.authenticate(auth);
        assertNotNull(SecurityContextHolder.getContext());
        final PersonDetails details = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        assertEquals(person.getUsername(), details.getUsername());
        assertTrue(result.isAuthenticated());
    }


    @Test(expected = UsernameNotFoundException.class)
    public void authenticateThrowsUserNotFoundExceptionForUnknownUsername() {
        final Authentication auth = new UsernamePasswordAuthenticationToken("unknownUsername", rawPassword);
        try {
            provider.authenticate(auth);
        } finally {
            final SecurityContext context = SecurityContextHolder.getContext();
            assertNull(context.getAuthentication());
        }
    }

    @Test(expected = BadCredentialsException.class)
    @Ignore
    public void authenticateThrowsBadCredentialsForInvalidPassword() {
        final Authentication auth = new UsernamePasswordAuthenticationToken(person.getUsername(), "unknownPassword");
        try {
            provider.authenticate(auth);
        } finally {
            final SecurityContext context = SecurityContextHolder.getContext();
            assertNull(context.getAuthentication());
        }
    }

    @Test
    public void supportsUsernameAndPasswordAuthentication() {
        assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @Ignore
    public void successfulLoginErasesPasswordInSecurityContextUser() {
        final Authentication auth = new UsernamePasswordAuthenticationToken(person.getUsername(), rawPassword);
        provider.authenticate(auth);
        assertNotNull(SecurityContextHolder.getContext());
        final PersonDetails details = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        assertNull(details.getUser().getPassword());
    }
}
