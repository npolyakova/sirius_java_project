package ru.hpclab.hl.module1.service;

import ru.hpclab.hl.module1.model.Guest;
import ru.hpclab.hl.module1.repository.GuestRepository;

import java.util.List;

public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Guest getGuestById(String id) {
        return guestRepository.findById(Long.getLong(id));
    }

    public Guest saveGuest(Guest user) {
        return guestRepository.save(user);
    }

    public void deleteGuest(String id) {
        guestRepository.delete(Long.getLong(id));
    }

    public Guest updateGuest(String id, Guest guest) {
        guest.setId(Long.getLong(id));
        return guestRepository.put(guest);
    }
}
