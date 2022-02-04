package cz.cvut.fel.ear.libraria.security;

import cz.cvut.fel.ear.libraria.security.util.*;
import cz.cvut.fel.ear.libraria.service.*;
import cz.cvut.fel.ear.libraria.config.*;
import cz.cvut.fel.ear.libraria.environment.config.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = {TestSecurityConfig.class, RestConfig.class})
public class AuthenticationFailureTest extends BaseServiceTestRunner {

    @Autowired
    private AuthenticationFailure failure;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void authenticationFailureReturnsLoginStatusWithErrorInfo() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final String msg = "Username not found";
        final AuthenticationException e = new UsernameNotFoundException(msg);
        failure.onAuthenticationFailure(request, response, e);
        final LoginStatus status = mapper.readValue(response.getContentAsString(), LoginStatus.class);
        assertFalse(status.isSuccess());
        assertFalse(status.isLoggedIn());
        assertNull(status.getUsername());
        assertEquals(msg, status.getErrorMessage());
    }
}
