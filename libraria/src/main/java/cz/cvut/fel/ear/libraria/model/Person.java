package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Person.findByUsername", query = "SELECT p FROM Person p WHERE p.username = :username"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password"),
    @NamedQuery(name = "Person.findByRole", query = "SELECT p FROM Person p WHERE p.role = :role"),
    @NamedQuery(name = "Person.findByDateOfBirth", query = "SELECT p FROM Person p WHERE p.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email"),
    @NamedQuery(name = "Person.findByPhoneNumber", query = "SELECT p FROM Person p WHERE p.phoneNumber = :phoneNumber"),
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "person_id"))
})
@Data
public class Person extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "username", nullable = false)
    private String username;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

    @Basic(optional = false)
    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Basic(optional = false)
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(targetEntity = Borrowing.class, cascade = CascadeType.ALL)
    @JoinColumn(name="person_id", referencedColumnName = "person_id")
    @OrderBy("endingDate DESC")
    @JsonIgnore
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
