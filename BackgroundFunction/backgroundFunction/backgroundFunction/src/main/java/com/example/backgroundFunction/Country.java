package com.example.backgroundFunction;
import javax.persistence.*;

@Entity
@Table(name = "countries")
public class Country extends AuditModel {
    @Id
    @GeneratedValue(generator = "country_generator")
    @SequenceGenerator(
            name = "country_generator",
            sequenceName = "country_sequence",
            initialValue = 1
    )
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return name;
    }
}
