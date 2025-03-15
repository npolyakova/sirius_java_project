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
        return dto;
    }

    public Guest mapToPGuest(GuestDto dto) {
        Guest entity = new Guest();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setPassport(dto.getPassport());
        return entity;

    }
}
