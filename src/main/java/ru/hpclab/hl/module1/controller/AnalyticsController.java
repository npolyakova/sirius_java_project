package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hpclab.hl.module1.service.BookingService;
import ru.hpclab.hl.module1.service.HotelRoomService;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private final BookingService bookingService;
    private final HotelRoomService roomService;

    public AnalyticsController(BookingService bookingService, HotelRoomService roomService, HotelRoomService roomService1) {
        this.bookingService = bookingService;
        this.roomService = roomService1;
    }

    @GetMapping("/")
    public Map<String, Map<String, Double>> getStatistics(){
        return bookingService.getAverageOccupancyByRoomCategory();
    }

}
