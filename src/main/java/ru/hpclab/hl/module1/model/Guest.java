package ru.hpclab.hl.module1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Date;

@Getter
@Setter
public class Guest {

    @NonNull
    private long id;
    @NonNull
    private String fullName;
    @NonNull
    private String passport;
    private Date checkIn;
    private Date checkOut;

    public Guest(long id, String fullName, String passport) {
        this.id = id;
        this.fullName = fullName;
        this.passport = passport;
    }
}
