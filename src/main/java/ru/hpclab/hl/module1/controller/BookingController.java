package ru.hpclab.hl.module1.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.service.BookingService;
import ru.hpclab.hl.module1.service.ObservabilityService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    private ObservabilityService observabilityService;

    @GetMapping("")
    public List<BookingDto> getBookings() {
        observabilityService.start();
        List<BookingDto> booking = bookingService.getAllBookings();
        observabilityService.start();
        return booking;
    }

    @GetMapping("/{id}")
    public Optional<BookingDto> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @ApiIgnore
    @DeleteMapping("/")
    public void deleteBookings() {
        bookingService.deleteAll();
    }

    @PostMapping("")
    public BookingDto saveBooking(@RequestBody BookingDto client) throws ParseException {
        return bookingService.saveBooking(client);
    }

    @PutMapping("")
    public BookingDto updateBooking(@RequestBody BookingDto client) throws ParseException {
        return bookingService.saveBooking(client);
    }
}
