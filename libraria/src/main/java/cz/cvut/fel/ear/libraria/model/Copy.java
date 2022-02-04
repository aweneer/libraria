package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "copy", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Copy.findByLocationId", query = "SELECT c FROM Copy c WHERE c.locationId = :locationId"),
    @NamedQuery(name = "Copy.findByBookId", query = "SELECT c FROM Copy c WHERE c.bookId = :bookId"),
    @NamedQuery(name = "Copy.findBySignature", query = "SELECT c FROM Copy c WHERE c.signature = :signature"),
    @NamedQuery(name = "Copy.findByPublisher", query = "SELECT c FROM Copy c WHERE c.publisher = :publisher"),
    @NamedQuery(name = "Copy.findByDateOfPublication", query = "SELECT c FROM Copy c WHERE c.dateOfPublication = :dateOfPublication"),
    @NamedQuery(name = "Copy.findByPagesCount", query = "SELECT c FROM Copy c WHERE c.pagesCount = :pagesCount"),
    @NamedQuery(name = "Copy.findByIsbn", query = "SELECT c FROM Copy c WHERE c.isbn = :isbn")
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "copy_id"))
})
@Data
public class Copy extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "location_id", nullable = false, insertable = false, updatable = false)
    private int locationId;

    @Basic(optional = false)
    @Column(name = "book_id", nullable = false, insertable = false, updatable = false)
    private int bookId;

    @Basic(optional = false)
    @Column(name = "signature", nullable = false)
    private String signature;

    @Basic(optional = false)
    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "date_of_publication")
    private Date dateOfPublication;

    @Column(name = "pages_count")
    private Integer pagesCount;

    @Basic(optional = false)
    @Column(name = "isbn", nullable = false)
    private int isbn;

    @ManyToOne(targetEntity = Location.class, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="location_id", referencedColumnName = "location_id")
    @JsonIgnore
    private Location location;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="book_id", referencedColumnName = "book_id")
    @JsonIgnore
    private Book book;

    @OneToMany(targetEntity = Borrowing.class, cascade = CascadeType.ALL)
    @JoinColumn(name="copy_id", referencedColumnName = "copy_id")
    @JsonIgnore
    @OrderBy("endingDate DESC")
    private List<Borrowing> borrowings = new ArrayList<>();

    //Adds borrowing to borrowings
    public void addBorrowing(Borrowing borrowing) {
        Objects.requireNonNull(borrowing);
        if (!borrowings.contains(borrowing)) {
            borrowings.add(borrowing);
        }
    }

    //Removes borrowing from borrowings
    public void removeBorrowing(Borrowing borrowing) {
        Objects.requireNonNull(borrowing);
        if (borrowings.contains(borrowing)) {
            borrowings.remove(borrowing);
            //borrowings.removeIf(t -> Objects.equals(t.getId(), borrowing.getId()));
        }
    }

    @Override
    public String toString() {
        return "Entity";
    }
}
