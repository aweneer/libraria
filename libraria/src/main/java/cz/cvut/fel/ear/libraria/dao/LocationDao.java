package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Location;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class LocationDao extends BaseDao<Location> {

    public LocationDao() {super(Location.class);}

    public List<Location> findByFloor(int floor) {
        try {
            return em.createNamedQuery("Location.findByFloor", Location.class).setParameter("floor", floor).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Location> findBySection(String section) {
        try {
            return em.createNamedQuery("Location.findBySection", Location.class).setParameter("section", section).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Location> findByShelf(String shelf) {
        try {
            return em.createNamedQuery("Location.findByShelf", Location.class).setParameter("shelf", shelf).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
