package ru.hpclab.hl.module1.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hpclab.hl.module1.entities.HotelRoom;

import java.util.*;

import static java.lang.String.format;

public interface HotelRoomRepository extends CrudRepository<HotelRoom, Long> {
    List<HotelRoom> findAll();

    HotelRoom findById(long id);

    HotelRoom save(HotelRoom room);

    void delete(HotelRoom room);

//    public static final String ROOM_NOT_FOUND_MSG = "HotelRoom with ID %s not found";
//    public static final String ROOM_EXISTS_MSG = "HotelRoom with ID %s is already exists";
//
//    private final Map<Long, HotelRoom> rooms = new HashMap<>();
//
//    public List<HotelRoom> findAll() {
//        return new ArrayList<>(rooms.values());
//    }
//
//    public HotelRoom findById(long id) {
//        final var room = rooms.get(id);
//        if (room == null) {
//            throw new CustomException(format(ROOM_NOT_FOUND_MSG, id));
//        }
//        return room;
//    }
//
//    public void delete(long id) {
//        final var removed = rooms.remove(id);
//        if (removed == null) {
//            throw new CustomException(format(ROOM_NOT_FOUND_MSG, id));
//        }
//    }
//
//    public HotelRoom save(HotelRoom room) {
//        if (ObjectUtils.isEmpty(room.getId())) {
//            room.setId(new Random().nextLong());
//        }
//
//        final var userData = rooms.get(room.getId());
//        if (userData != null) {
//            throw new CustomException(format(ROOM_EXISTS_MSG, room.getId()));
//        }
//
//        rooms.put(room.getId(), room);
//
//        return room;
//    }
//
//    public HotelRoom put(HotelRoom room) {
//        final var userData = rooms.get(room.getId());
//        if (userData == null) {
//            throw new CustomException(format(ROOM_NOT_FOUND_MSG, room.getId()));
//        }
//
//        final var removed = rooms.remove(room.getId());
//        if (removed != null) {
//            rooms.put(room.getId(), room);
//        } else {
//            throw new CustomException(format(ROOM_NOT_FOUND_MSG, room.getId()));
//        }
//
//        return room;
//    }
//
//    public void clear(){
//        rooms.clear();
//    }
}
