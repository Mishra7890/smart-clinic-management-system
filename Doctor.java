// package com.yourpackage.models;   // अगर चाहिए तो अपने प्रोजेक्ट के हिसाब से बदलें

import javax.persistence.*;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String contact;

    // Available times field, type as needed (list ya string, yahan string jaise "Mon 9-12, Tue 2-5")
    private String availableTimes;

    // Default Constructor (जरूरी है JPA के लिए)
    public Doctor() {}

    // Parameterized Constructor
    public Doctor(String name, String specialization, String contact, String availableTimes) {
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.availableTimes = availableTimes;
    }

    // Getter और Setter - IDE से generate करा सकते हैं
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(String availableTimes) {
        this.availableTimes = availableTimes;
    }
}
