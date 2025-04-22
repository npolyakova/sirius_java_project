package ru.hpclab.hl.module1.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.entities.HotelRoom;
import ru.hpclab.hl.module1.service.HotelRoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class HotelRoomController {
    private final HotelRoomService roomService;

    @Autowired
    public HotelRoomController(HotelRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("")
    public List<HotelRoom> getHotelRooms() {
        return roomService.getAllHotelRooms();
    }

    @GetMapping("/{id}")
    public Optional<HotelRoom> getHotelRoomById(@PathVariable String id) {
        return roomService.getHotelRoomById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHotelRoom(@PathVariable String id) {
        roomService.deleteHotelRoom(id);
    }

    @PostMapping("")
    public HotelRoom saveHotelRoom(@RequestBody HotelRoom client) {
        return roomService.saveHotelRoom(client);
    }

    @PutMapping("")
    public HotelRoom updateHotelRoom(@RequestBody HotelRoom client) {
        return roomService.saveHotelRoom(client);
    }

    @ApiIgnore
    @DeleteMapping("")
    public void deleteAllRooms() {
        roomService.deleteAll();
    }
}
