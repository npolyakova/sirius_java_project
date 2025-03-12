package ru.hpclab.hl.module1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class HotelRoom {

    @NonNull
    private long id;
    @NonNull
    private boolean type;
    @NonNull
    private long costPerNight;
}
