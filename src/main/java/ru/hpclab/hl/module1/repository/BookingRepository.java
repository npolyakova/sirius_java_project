package ru.hpclab.hl.module1.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hpclab.hl.module1.entities.Booking;


import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findAll();

    Booking findById(long id);

    Booking save(Booking booking);

    void deleteAll();

}
