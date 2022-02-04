package cz.cvut.fel.ear.libraria.service;

import cz.cvut.fel.ear.libraria.dao.LocationDao;
import cz.cvut.fel.ear.libraria.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LocationService {

    private final LocationDao dao;

    @Autowired
    public LocationService(LocationDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void persist(Location location) {
        Objects.requireNonNull(location);
        dao.persist(location);
    }

    @Transactional(readOnly = true)
    public Location find(Integer id) {
        return dao.find(id);
    }

    @Transactional(readOnly = true)
    public List<Location> findAll() {
        return dao.findAll();
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void update(Location location) {
        dao.update(location);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Location location) {
        Objects.requireNonNull(location);
        dao.remove(location);
    }
}
