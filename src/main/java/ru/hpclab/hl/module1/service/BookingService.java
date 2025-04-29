package ru.hpclab.hl.module1.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.utils.BookingMapper;
import ru.hpclab.hl.module1.utils.ObservabilityService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static ru.hpclab.hl.module1.utils.BookingMapper.mapToBookingEntity;
import static ru.hpclab.hl.module1.utils.BookingMapper.mapToDto;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream().map(BookingMapper::mapToDto).toList();
    }

    public Optional<BookingDto> getBookingById(String id) {
        return Optional.of(mapToDto(bookingRepository.findById(Long.valueOf(id)).get()));
    }

    @Transactional
    public BookingDto saveBooking(BookingDto booking) throws ParseException {
        return mapToDto(bookingRepository.save(mapToBookingEntity(booking)));
    }

    @Transactional
    public void deleteAll() {
        bookingRepository.deleteAll();
    }

    @Transactional
    public void getAverageOccupancyByRoomCategory() {
//        this.getAllBookings().stream().filter(bookingDto -> {
//            String dates = bookingDto.getDates();
//            if (dates)
//        })
    }
}
