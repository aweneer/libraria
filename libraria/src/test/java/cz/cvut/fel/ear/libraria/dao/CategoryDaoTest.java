package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.environment.Generator;
import cz.cvut.fel.ear.libraria.model.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CategoryDaoTest extends BaseDaoTestRunner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CategoryDao dao;

    private Category category;

    @Before
    public void setUp() {
        //Category
        category = Generator.generateCategory();
        em.persist(category);
    }

    @Test
    public void findByNameReturnsPersonWithMatchingName() {
        final List<Category> results = dao.findByName(category.getName());
        assertNotNull(results);

        Category result = null;
        for(Category resultElement : results) {
            if(category.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByNameReturnsEmptyListForUnknownName() {
        final List<Category> results = dao.findByName("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void findByDescriptionReturnsPersonWithMatchingDescription() {
        final List<Category> results = dao.findByDescription(category.getDescription());
        assertNotNull(results);

        Category result = null;
        for(Category resultElement : results) {
            if(category.getId().equals(resultElement.getId())) result = resultElement;
        }

        assertNotNull(result);
    }

    @Test
    public void findByDescriptionReturnsEmptyListForUnknownDescription() {
        final List<Category> results = dao.findByDescription("unknown");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
