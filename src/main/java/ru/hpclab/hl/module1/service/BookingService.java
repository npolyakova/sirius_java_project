package ru.hpclab.hl.module1.service;

import ru.hpclab.hl.module1.model.Booking;
import ru.hpclab.hl.module1.repository.BookingRepository;

import java.util.List;

public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(Long.getLong(id));
    }

    public Booking saveBooking(Booking user) {
        return bookingRepository.save(user);
    }

    public void deleteBooking(String id) {
        bookingRepository.delete(Long.getLong(id));
    }

    public Booking updateBooking(String id, Booking booking) {
        booking.setId(Long.getLong(id));
        return bookingRepository.put(booking);
    }
}
