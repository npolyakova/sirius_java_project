package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import ru.hpclab.hl.module1.controller.exception.GuestException;
import ru.hpclab.hl.module1.model.Guest;

//TODO
import java.util.*;

import static java.lang.String.format;

@Repository
public class GuestRepository {
    public static final String GUEST_NOT_FOUND_MSG = "Guest with ID %s not found";
    public static final String GUEST_EXISTS_MSG = "Guest with ID %s is already exists";

    private final Map<Long, Guest> guests = new HashMap<>();

    public List<Guest> findAll() {
        return new ArrayList<>(guests.values());
    }

    public Guest findById(long id) {
        final var guest = guests.get(id);
        if (guest == null) {
            throw new GuestException(format(GUEST_NOT_FOUND_MSG, id));
        }
        return guest;
    }

    public void delete(long id) {
        final var removed = guests.remove(id);
        if (removed == null) {
            throw new GuestException(format(GUEST_NOT_FOUND_MSG, id));
        }
    }

    public Guest save(Guest guest) {
        if (ObjectUtils.isEmpty(guest.getId())) {
            guest.setId(new Random().nextLong());
        }

        final var userData = guests.get(guest.getId());
        if (userData != null) {
            throw new GuestException(format(GUEST_EXISTS_MSG, guest.getId()));
        }

        guests.put(guest.getId(), guest);

        return guest;
    }

    public Guest put(Guest guest) {
        final var userData = guests.get(guest.getId());
        if (userData == null) {
            throw new GuestException(format(GUEST_NOT_FOUND_MSG, guest.getId()));
        }

        final var removed = guests.remove(guest.getId());
        if (removed != null) {
            guests.put(guest.getId(), guest);
        } else {
            throw new GuestException(format(GUEST_NOT_FOUND_MSG, guest.getId()));
        }

        return guest;
    }

    public void clear(){
        guests.clear();
    }
}
