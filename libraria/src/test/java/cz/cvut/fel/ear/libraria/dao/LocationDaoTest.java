package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocationDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LocationDao dao;

    private Location location;

    @Before
    public void setUp() {
        //Location
        location = Generator.generateLocation();
        em.persist(location);
    }

    @Test
    public void findByFloorReturnsPersonWithMatchingFloor() {
        final List<Location> results = dao.findByFloor(location.getFloor());
        assertNotNull(results);

        Location result = null;
        for(Location resultElement : results) {
            if(location.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByFloorReturnsEmptyListForUnknownFloor() {
        final List<Location> results = dao.findByFloor(-1000);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findBySectionReturnsPersonWithMatchingSection() {
        final List<Location> results = dao.findBySection(location.getSection());
        assertNotNull(results);

        Location result = null;
        for(Location resultElement : results) {
            if(location.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findBySectionReturnsEmptyListForUnknownSection() {
        final List<Location> results = dao.findBySection("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByShelfReturnsPersonWithMatchingShelf() {
        final List<Location> results = dao.findByShelf(location.getShelf());
        assertNotNull(results);

        Location result = null;
        for(Location resultElement : results) {
            if(location.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByShelfReturnsEmptyListForUnknownShelf() {
        final List<Location> results = dao.findByShelf("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
