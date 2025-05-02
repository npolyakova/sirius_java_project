package ru.hpclab.hl.module1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.dto.BookingDto;
import ru.hpclab.hl.module1.repository.BookingRepository;
import ru.hpclab.hl.module1.utils.BookingMapper;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.hpclab.hl.module1.utils.BookingMapper.mapToBookingEntity;
import static ru.hpclab.hl.module1.utils.BookingMapper.mapToDto;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ObservabilityService observabilityService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<BookingDto> getAllBookings() {
        observabilityService.start();
        List<BookingDto> allBookings = bookingRepository.findAll().stream().map(BookingMapper::mapToDto).toList();
        observabilityService.stop();
        return allBookings;
    }

    public Optional<BookingDto> getBookingById(String id) {
        Optional<BookingDto> bookingDto = Optional.of(mapToDto(bookingRepository.findById(Long.valueOf(id)).get()));
        return bookingDto;
    }

    @Transactional
    public BookingDto saveBooking(BookingDto booking) throws ParseException {
        BookingDto bookingDto = mapToDto(bookingRepository.save(mapToBookingEntity(booking)));
        return bookingDto;
    }

    @Transactional
    public void deleteAll() {
        bookingRepository.deleteAll();
    }

    @Transactional
    public Map<String, Map<String, Double>> getAverageOccupancyByRoomCategory() {
        observabilityService.start();
        String sql = """
        WITH date_range AS (
            SELECT generate_series(
                date_trunc('month', MIN(arrival_date)),
                date_trunc('month', MAX(leaving_date)),
                interval '1 month'
            ) AS month
            FROM booking
        ),
        room_types AS (
            SELECT DISTINCT type FROM hotel_room
        ),
        monthly_occupancy AS (
            SELECT 
                TO_CHAR(dr.month, 'YYYY-MM') AS month,
                rt.type AS room_type,
                COUNT(DISTINCT hr.id) AS occupied_rooms,
                (SELECT COUNT(*) FROM hotel_room WHERE type = rt.type) AS total_rooms
            FROM 
                date_range dr
            CROSS JOIN 
                room_types rt
            LEFT JOIN 
                hotel_room hr ON hr.type = rt.type
            LEFT JOIN 
                booking b ON b.room_id = hr.id AND
                           b.arrival_date <= (dr.month + interval '1 month' - interval '1 day')::date AND
                           b.leaving_date >= dr.month::date
            GROUP BY 
                TO_CHAR(dr.month, 'YYYY-MM'), rt.type
        )
        SELECT 
            month,
            room_type,
            CASE 
                WHEN total_rooms = 0 THEN 0
                ELSE occupied_rooms::float / total_rooms
            END AS occupancy_rate
        FROM 
            monthly_occupancy
        WHERE 
            room_type IS NOT NULL
        ORDER BY 
            month, room_type
        """;

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        Map<String, Map<String, Double>> result = new HashMap<>();

        for (Object[] row : results) {
            String month = (String) row[0];
            String roomType = (String) row[1];
            double occupancyRate = ((Number) row[2]).doubleValue();

            result.computeIfAbsent(month, k -> new HashMap<>())
                    .put(roomType, occupancyRate);
        }

        observabilityService.stop();
        return result;
    }
}
