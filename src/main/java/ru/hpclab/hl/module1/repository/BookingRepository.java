package ru.hpclab.hl.module1.repository;

import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import ru.hpclab.hl.module1.controller.exception.CustomException;
import ru.hpclab.hl.module1.model.Booking;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.String.format;

@Repository
public class BookingRepository {
    public static final String BOOKING_NOT_FOUND_MSG = "Booking with ID %s not found";
    public static final String BOOKING_EXISTS_MSG = "Booking with ID %s is already exists";

    private final Map<Long, Booking> bookings = new HashMap<>();

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public Booking findById(long id) {
        final var booking = bookings.get(id);
        if (booking == null) {
            throw new CustomException(format(BOOKING_NOT_FOUND_MSG, id));
        }
        return booking;
    }

    public void delete(long id) {
        final var removed = bookings.remove(id);
        if (removed == null) {
            throw new CustomException(format(BOOKING_NOT_FOUND_MSG, id));
        }
    }

    public Booking save(Booking booking) {
        if (ObjectUtils.isEmpty(booking.getId())) {
            booking.setId(new Random().nextLong());
        }

        final var userData = bookings.get(booking.getId());
        if (userData != null) {
            throw new CustomException(format(BOOKING_EXISTS_MSG, booking.getId()));
        }

        bookings.put(booking.getId(), booking);

        return booking;
    }

    public Booking put(Booking booking) {
        final var userData = bookings.get(booking.getId());
        if (userData == null) {
            throw new CustomException(format(BOOKING_NOT_FOUND_MSG, booking.getId()));
        }

        final var removed = bookings.remove(booking.getId());
        if (removed != null) {
            bookings.put(booking.getId(), booking);
        } else {
            throw new CustomException(format(BOOKING_NOT_FOUND_MSG, booking.getId()));
        }

        return booking;
    }

    public void clear(){
        bookings.clear();
    }
}
