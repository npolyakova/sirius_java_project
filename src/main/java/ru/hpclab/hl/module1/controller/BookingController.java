package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.entities.Booking;
import ru.hpclab.hl.module1.service.BookingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("")
    public List<BookingDto> getBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Optional<BookingDto> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

//    @DeleteMapping("/{id}")
//    public void deleteBooking(@PathVariable String id) {
//        bookingService.deleteBooking(id);
//    }

    @PostMapping("")
    public BookingDto saveBooking(@RequestBody Booking client) {
        return bookingService.saveBooking(client);
    }
}
