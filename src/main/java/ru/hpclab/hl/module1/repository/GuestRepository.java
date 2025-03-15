package ru.hpclab.hl.module1.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hpclab.hl.module1.dto.GuestDto;
import ru.hpclab.hl.module1.entities.Guest;

//TODO
import java.util.*;

public interface GuestRepository extends CrudRepository<Guest, Long> {

    List<Guest> findAll();

    Guest findById(long id);

    Guest save(GuestDto guest);

//    void delete(GuestDto guest);
}
