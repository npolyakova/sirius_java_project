package ru.hpclab.hl.module1.dto;

import lombok.Data;
import ru.hpclab.hl.module1.entities.RoomType;

@Data
public class HotelRoomDto {
    long id;
    RoomType type;
    long costPerNight;
}
