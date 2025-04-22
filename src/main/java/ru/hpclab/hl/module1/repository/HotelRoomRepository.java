package ru.hpclab.hl.module1.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hpclab.hl.module1.entities.HotelRoom;

import java.util.*;

public interface HotelRoomRepository extends CrudRepository<HotelRoom, Long> {
    List<HotelRoom> findAll();

    HotelRoom findById(long id);

    HotelRoom save(HotelRoom room);

    void delete(HotelRoom room);

    void deleteAll();
}
