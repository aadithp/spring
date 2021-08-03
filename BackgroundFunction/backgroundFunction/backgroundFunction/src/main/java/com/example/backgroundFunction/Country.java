package com.example.backgroundFunction;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity
@Table(name = "countries")
@RedisHash("Country")
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
