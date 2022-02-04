package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Author.findByFirstName", query = "SELECT a FROM Author a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Author.findByLastName", query = "SELECT a FROM Author a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Author.findByBiography", query = "SELECT a FROM Author a WHERE a.biography = :biography")
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "author_id"))
})
@Data
public class Author extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "biography")
    private String biography;

    @ManyToMany(targetEntity = Book.class, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "written",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    //Adds title to author
    public void addBook(Book book) {
        Objects.requireNonNull(book);
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public void removeTitle(Book book) {
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
