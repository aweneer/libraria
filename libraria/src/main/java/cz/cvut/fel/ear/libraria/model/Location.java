package cz.cvut.fel.ear.libraria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "location", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Location.findByFloor", query = "SELECT l FROM Location l WHERE l.floor = :floor"),
    @NamedQuery(name = "Location.findBySection", query = "SELECT l FROM Location l WHERE l.section = :section"),
    @NamedQuery(name = "Location.findByShelf", query = "SELECT l FROM Location l WHERE l.shelf = :shelf"),
})
@AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "location_id"))
})
@Data
public class Location extends AbstractEntity {

    @Basic(optional = false)
    @Column(name = "floor", nullable = false)
    private int floor;

    @Basic(optional = false)
    @Column(name = "section", nullable = false)
    private String section;

    @Basic(optional = false)
    @Column(name = "shelf", nullable = false)
    private String shelf;

    @OneToMany(targetEntity = Copy.class, cascade = CascadeType.ALL)
    @JoinColumn(name="location_id", referencedColumnName = "location_id")
    @OrderBy("isbn ASC")
    @JsonIgnore
    private List<Copy> copies = new ArrayList<>();

    // Adds copy to location
    public void addCopy(Copy copy) {
        Objects.requireNonNull(copy);
        if (!copies.contains(copy)) {
            copies.add(copy);
        }
    }

    // Removes copy from location
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
