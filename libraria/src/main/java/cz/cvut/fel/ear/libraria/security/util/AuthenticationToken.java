package cz.cvut.fel.ear.libraria.security.util;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken implements Principal {

    private PersonDetails personDetails;

    public AuthenticationToken(Collection<? extends GrantedAuthority> authorities, PersonDetails userDetails) {
        super(authorities);
        this.personDetails = userDetails;
        super.setAuthenticated(true);
        super.setDetails(userDetails);
    }

    @Override
    public String getCredentials() {
            return personDetails.getPassword();
    }

    @Override
    public PersonDetails getPrincipal() {
            return personDetails;
        }
    }


