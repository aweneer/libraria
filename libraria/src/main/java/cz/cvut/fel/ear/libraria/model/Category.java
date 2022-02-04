package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name"),
    @NamedQuery(name = "Category.findByDescription", query = "SELECT c FROM Category c WHERE c.description = :description")
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "category_id"))
})
@Data
public class Category extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Category.class, cascade = CascadeType.ALL)
    @JoinColumn(name="category_id", referencedColumnName = "category_id")
    @JsonIgnore
    @OrderBy("name ASC")
    private List<Book> books = new ArrayList<>();

    //Adds title to category
    public void addBook(Book book) {
        Objects.requireNonNull(book);
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    //Removes title from category
    public void removeBook(Book book) {
        Objects.requireNonNull(book);
        if (books.contains(book)) {
            books.remove(book);
            //titles.removeIf(t -> Objects.equals(t.getId(), title.getId()));
        }
    }

    @Override
    public String toString() {
        return "Entity";
    }
}
