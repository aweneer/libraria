package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "borrowing", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Borrowing.findByCopyId", query = "SELECT b FROM Borrowing b WHERE b.copyId = :copyId"),
    @NamedQuery(name = "Borrowing.findByPersonId", query = "SELECT b FROM Borrowing b WHERE b.personId = :personId"),
    @NamedQuery(name = "Borrowing.findByStartingDate", query = "SELECT b FROM Borrowing b WHERE b.startingDate = :startingDate"),
    @NamedQuery(name = "Borrowing.findByEndingDate", query = "SELECT b FROM Borrowing b WHERE b.endingDate = :endingDate")
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "borrowing_id"))
})
@Data
public class Borrowing extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "copy_id", nullable = false, insertable = false, updatable = false)
    private int copyId;

    @Basic(optional = false)
    @Column(name = "person_id", nullable = false, insertable = false, updatable = false)
    private int personId;

    @Basic(optional = false)
    @Column(name = "starting_date", nullable = false)
    private Date startingDate;

    @Basic(optional = false)
    @Column(name = "ending_date", nullable = false)
    private Date endingDate;

    @ManyToOne(targetEntity = Copy.class, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="copy_id", referencedColumnName = "copy_id")
    @JsonIgnore
    private Copy copy;

    @ManyToOne(targetEntity = Person.class, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="person_id", referencedColumnName = "person_id")
    @JsonIgnore
    private Person person;

    @Override
    public String toString() {
        return "Entity";
    }
}
