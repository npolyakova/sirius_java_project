package ru.hpclab.hl.module1.utils;

import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.entities.Booking;
import ru.hpclab.hl.module1.entities.Guest;
import ru.hpclab.hl.module1.entities.HotelRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class BookingMapper {
    public static BookingDto mapToDto(Booking entity) {
        BookingDto dto = new BookingDto();
        dto.setId(entity.getId());
        dto.setRoomId(entity.getRoom().getId());
        dto.setGuestId(entity.getGuest().getId());
        dto.setDateArr(entity.getArrivalDate());
        dto.setDateLeave(entity.getLeavingDate());
        return dto;
    }

    public static Booking mapToBookingEntity(BookingDto dto) throws ParseException {
        Booking entity = new Booking();
        entity.setId(dto.getId());
        entity.setRoom(new HotelRoom(dto.getRoomId()));
        entity.setGuest(new Guest(dto.getGuestId()));
        entity.setArrivalDate(dto.getDateArr());
        entity.setLeavingDate(dto.getDateLeave());
        return entity;
    }
}
