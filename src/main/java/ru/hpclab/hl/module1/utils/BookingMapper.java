package ru.hpclab.hl.module1.utils;

import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.entities.Booking;
import ru.hpclab.hl.module1.entities.Guest;
import ru.hpclab.hl.module1.entities.HotelRoom;

public class BookingMapper {
    public static BookingDto mapToDto(Booking entity) {
        BookingDto dto = new BookingDto();
        dto.setId(entity.getId());
        dto.setRoomId(entity.getRoom().getId());
        dto.setGuestId(entity.getGuest().getId());

        return dto;
    }

    public static Booking mapToBookingEntity(BookingDto dto) {
        Booking entity = new Booking();
        entity.setId(dto.getId());
        entity.setRoom(new HotelRoom(dto.getRoomId()));
        entity.setGuest(new Guest(dto.getGuestId()));
        return entity;
    }
}
