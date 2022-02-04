package cz.cvut.fel.ear.libraria.service.security;

import cz.cvut.fel.ear.libraria.dao.PersonDao;
import cz.cvut.fel.ear.libraria.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final PersonDao personDao;

    @Autowired
    public PersonDetailsService(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        List<Person> pl =  personDao.findByUsername(username);
        Person person = null;
        for (int i = 0; i < pl.size(); i++) {
            if (pl.get(i).getUsername() == username) {
                person = pl.get(i);
            }
        }
        System.out.println(person == null);
        if (person == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        return new cz.cvut.fel.ear.libraria.security.util.PersonDetails(person);
    }
}
