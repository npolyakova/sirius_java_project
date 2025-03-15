package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hpclab.hl.module1.entities.Booking;
import ru.hpclab.hl.module1.service.BookingService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private final BookingService bookingService;
    public AnalyticsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/guests")
    @Query("select average(count(id)) from guest")
    public int getBookings(String query) {
//        if (Objects.equals(query, "guestsPerMonth")) {
//
//        }
//        return bookingService.getAllBookings();
        return 11;
    }

}
