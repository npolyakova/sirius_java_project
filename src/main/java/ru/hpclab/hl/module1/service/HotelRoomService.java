package ru.hpclab.hl.module1.service;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.entities.HotelRoom;
import ru.hpclab.hl.module1.repository.HotelRoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@Table(name = "hotel_room")
public class HotelRoomService {

    @Autowired
    private HotelRoomRepository roomRepository;

    @Transactional
    public List<HotelRoom> getAllHotelRooms() {
        return roomRepository.findAll();
    }

    public Optional<HotelRoom> getHotelRoomById(String id) {
        return roomRepository.findById(Long.getLong(id));
    }

    public HotelRoom saveHotelRoom(HotelRoom user) {
        return roomRepository.save(user);
    }

    public void deleteHotelRoom(String id) {
        var room = getHotelRoomById(id);
        room.ifPresent(hotelRoom -> roomRepository.delete(hotelRoom));
    }

    public void deleteAll() {
        roomRepository.deleteAll();
    }

//    public HotelRoom updateHotelRoom(String id, HotelRoom room) {
//        room.setId(Long.getLong(id));
//        return roomRepository.put(room);
//    }
}
