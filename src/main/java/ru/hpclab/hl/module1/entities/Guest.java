package ru.hpclab.hl.module1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NonNull
    private String fullName;

    @Column
    @NonNull
    private String passport;

    @Column
    @NonNull
    private String checkIn;

    @Column
    private String checkOut;

    @Column
    private boolean deleted;

    public Guest(long id) {
        this.id = id;
    }

    public Guest(){

    }
}
