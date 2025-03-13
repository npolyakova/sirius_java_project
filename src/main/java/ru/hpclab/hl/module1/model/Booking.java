package ru.hpclab.hl.module1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class Booking {

    @NonNull
    private long id;
    @NonNull
    private long guest;
    private long room;
    @NonNull
    private String dates;
    private double cost;

    public Booking(long id, long guest, String dates) {
        this.id = id;
        this.guest = guest;
        this.dates = dates;
    }

}
