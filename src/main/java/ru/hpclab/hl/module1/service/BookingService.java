package ru.hpclab.hl.module1.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.entities.Booking;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.utils.BookingMapper;

import java.util.List;
import java.util.Optional;

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
    public BookingDto saveBooking(Booking booking) {
        return mapToDto(bookingRepository.save(booking));
    }

    @Transactional
    public void deleteAll() {
        bookingRepository.deleteAll();
    }
}
