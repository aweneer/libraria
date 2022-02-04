package cz.cvut.fel.ear.libraria.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "written", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Written.findByBookId", query = "SELECT w FROM Written w WHERE w.bookId = :bookId"),
    @NamedQuery(name = "Written.findByAuthorId", query = "SELECT w FROM Written w WHERE w.authorId = :authorId")
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "written_id"))
})
@Data
public class Written extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "book_id", nullable = false)
    private int bookId;

    @Basic(optional = false)
    @Column(name = "author_id", nullable = false)
    private int authorId;

    @Override
    public String toString() {
        return "Entity";
    }
}
