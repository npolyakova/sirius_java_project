package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Booking;
import ru.hpclab.hl.module1.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("")
    public List<Booking> getBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
    }

    @PostMapping("")
    public Booking saveBooking(@RequestBody Booking client) {
        return bookingService.saveBooking(client);
    }

    @PutMapping(value = "/{id}")
    public Booking updateBooking(@PathVariable(required = false) String id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }
}
