package ru.hpclab.hl.module1.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.entities.Guest;
import ru.hpclab.hl.module1.repository.GuestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Transactional
    public List<Guest> getAllGuests() {
        return guestRepository.findAll().stream().toList();
    }

    public Optional<Guest> getGuestById(String id) {
        return Optional.of(guestRepository.findById(Long.valueOf(id)).get());
    }

    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public void deleteAll() {
        guestRepository.deleteAll();
    }
}
