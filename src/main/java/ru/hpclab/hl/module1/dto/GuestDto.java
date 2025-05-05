package ru.hpclab.hl.module1.dto;

import lombok.Data;

@Data
public class GuestDto {
    long id;
    String fullName;
    String passport;
    String checkIn;
    String checkOut;
}
