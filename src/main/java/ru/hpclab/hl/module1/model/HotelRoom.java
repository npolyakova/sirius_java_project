package ru.hpclab.hl.module1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class HotelRoom {

    public enum RoomType {
        LUX,
        ECONOM,
        STANDART
    }

    @NonNull
    private long id;
    @NonNull
    private RoomType type;
    @NonNull
    private long costPerNight;

    public HotelRoom(long id, RoomType type, long costPerNight) {
        this.id = id;
        this.type = type;
        this.costPerNight = costPerNight;
    }
}
