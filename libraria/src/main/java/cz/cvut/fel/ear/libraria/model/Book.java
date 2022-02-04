package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Book.findByCategoryId", query = "SELECT b FROM Book b WHERE b.categoryId = :categoryId"),
    @NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.name = :name"),
    @NamedQuery(name = "Book.findByLanguage", query = "SELECT b FROM Book b WHERE b.language = :language")
        // TODO: named query to find title by author
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "book_id"))
})
@Data
public class Book extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    private int categoryId;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "language")
    private String language;

    @ManyToOne(targetEntity = Category.class, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name="category_id", referencedColumnName = "category_id")
    @JsonIgnore
    private Category category;

    @OneToMany(targetEntity = Copy.class, cascade = CascadeType.ALL)
    @JoinColumn(name="book_id", referencedColumnName = "book_id")
    @JsonIgnore
    @OrderBy("isbn ASC")
    private List<Copy> copies = new ArrayList<>();

    @ManyToMany(targetEntity = Author.class, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "written",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonIgnore
    private List<Author> authors = new ArrayList<>();

    // Adds author to authors
    public void addAuthor(Author author) {
        Objects.requireNonNull(author);
        if (!authors.contains(author)) {
            authors.add(author);
        }
    }

    // Removes author from authors
    public void removeAuthor(Author author) {
        Objects.requireNonNull(author);
        if (authors.contains(author)) {
            authors.remove(author);
            //authors.removeIf(a -> Objects.equals(a.getId(), author.getId()));
        }
    }

    // Adds copy to copies
    public void addCopy(Copy copy) {
        Objects.requireNonNull(copy);
        if (!copies.contains(copy)) {
            copies.add(copy);
        }
    }

    // Removes copy from copies
    public void removeCopy(Copy copy) {
        Objects.requireNonNull(copy);
        if (copies.contains(copy)) {
            copies.remove(copy);
            //copies.removeIf(c -> Objects.equals(c.getId(), copy.getId()));
        }
    }

    @Override
    public String toString() {
        return "Entity";
    }
}
