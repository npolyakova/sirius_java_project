package ru.hpclab.hl.module1.utils;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.GuestDto;
import ru.hpclab.hl.module1.entities.Guest;

@Service
public class GuestMapper {

    public static GuestDto mapToGuestDto(Guest entity) {
        GuestDto dto = new GuestDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPassport(entity.getPassport());
        dto.setCheckIn(entity.getCheckIn());
        dto.setCheckIn(entity.getCheckOut());
        return dto;
    }

    public Guest mapToGuestEntity(GuestDto dto) {
        Guest entity = new Guest();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setPassport(dto.getPassport());
        return entity;

    }
}
