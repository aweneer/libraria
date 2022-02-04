package cz.cvut.fel.ear.libraria.security;

import cz.cvut.fel.ear.libraria.security.util.AuthenticationToken;
import cz.cvut.fel.ear.libraria.security.util.PersonDetails;
import cz.cvut.fel.ear.libraria.security.util.SecurityUtils;
import cz.cvut.fel.ear.libraria.service.security.PersonDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    private final PersonDetailsService personDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultAuthenticationProvider(PersonDetailsService personDetailsService, PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getCredentials().toString();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Authenticating user {}", username);
        }
        final PersonDetails userDetails = (PersonDetails) personDetailsService.loadUserByUsername(username);
        final String password = (String) authentication.getCredentials();
        // TODO: add Encoder later
        if(password.matches(userDetails.getPassword())) {
            userDetails.eraseCredentials();
            return SecurityUtils.setCurrentUser(userDetails);
        }
        // TODO: end of fix
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Provided credentials don't match.");
        }
        userDetails.eraseCredentials();
        return SecurityUtils.setCurrentUser(userDetails);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass) ||
                AuthenticationToken.class.isAssignableFrom(aClass);
    }
}
