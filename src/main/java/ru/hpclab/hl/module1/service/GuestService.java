package ru.hpclab.hl.module1.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.GuestDto;
import ru.hpclab.hl.module1.entities.Guest;
import ru.hpclab.hl.module1.repository.GuestRepository;
import ru.hpclab.hl.module1.utils.BookingMapper;
import ru.hpclab.hl.module1.utils.GuestMapper;

import java.util.List;
import java.util.Optional;

import static ru.hpclab.hl.module1.utils.BookingMapper.mapToDto;
import static ru.hpclab.hl.module1.utils.GuestMapper.mapToGuestDto;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Transactional
    public List<GuestDto> getAllGuests() {
        return guestRepository.findAll().stream().map(GuestMapper::mapToGuestDto).toList();
    }

    public Optional<GuestDto> getGuestById(String id) {
        return Optional.of(mapToGuestDto(guestRepository.findById(Long.valueOf(id)).get()));
    }

    public GuestDto saveGuest(GuestDto guest) {
        return mapToGuestDto(guestRepository.save(guest));
    }

    public void deleteAll() {
        guestRepository.deleteAll();
    }

//    public Guest updateGuest(String id, Guest guest) {
//        guest.setId(Long.getLong(id));
//        return guestRepository.put(guest);
//    }
}
