package ru.hpclab.hl.module1.dto;

import lombok.Data;

@Data
public class BookingDto {
    long id;
    long guestId;
    long roomId;
    String dates;
    double cost;
}
