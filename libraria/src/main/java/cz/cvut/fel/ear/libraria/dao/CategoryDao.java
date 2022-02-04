package cz.cvut.fel.ear.libraria.dao;

import cz.cvut.fel.ear.libraria.model.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class CategoryDao extends BaseDao<Category> {

    public CategoryDao() {super(Category.class);}

    public List<Category> findByName(String name) {
        try {
            return em.createNamedQuery("Category.findByName", Category.class).setParameter("name", name).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Category> findByDescription(String description) {
        try {
            return em.createNamedQuery("Category.findByDescription", Category.class).setParameter("description", description).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
