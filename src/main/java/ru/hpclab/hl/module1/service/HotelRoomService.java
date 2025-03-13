package ru.hpclab.hl.module1.service;

import ru.hpclab.hl.module1.model.HotelRoom;
import ru.hpclab.hl.module1.repository.HotelRoomRepository;

import java.util.List;

public class HotelRoomService {

    private final HotelRoomRepository roomRepository;

    public HotelRoomService(HotelRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<HotelRoom> getAllHotelRooms() {
        return roomRepository.findAll();
    }

    public HotelRoom getHotelRoomById(String id) {
        return roomRepository.findById(Long.getLong(id));
    }

    public HotelRoom saveHotelRoom(HotelRoom user) {
        return roomRepository.save(user);
    }

    public void deleteHotelRoom(String id) {
        roomRepository.delete(Long.getLong(id));
    }

    public HotelRoom updateHotelRoom(String id, HotelRoom room) {
        room.setId(Long.getLong(id));
        return roomRepository.put(room);
    }
}
