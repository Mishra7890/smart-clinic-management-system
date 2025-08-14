import javax.persistence.*;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String specialization;
    private String contact;

    // Add the required availableTimes field (example: as a String OR List)
    // TODO: use correct type as per your need!
    private String availableTimes;

    // Getters, Setters, Constructors as needed

    public Doctor() {}

    public Doctor(String name, String specialization, String contact, String availableTimes) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.availableTimes = availableTimes;
    }

    // Getters and setters...

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAvailableTimes() { return availableTimes; }
    public void setAvailableTimes(String availableTimes) { this.availableTimes = availableTimes; }
}
