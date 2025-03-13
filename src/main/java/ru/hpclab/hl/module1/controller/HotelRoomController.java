package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.HotelRoom;
import ru.hpclab.hl.module1.service.HotelRoomService;

import java.util.List;

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
    public HotelRoom getHotelRoomById(@PathVariable String id) {
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

    @PutMapping(value = "/{id}")
    public HotelRoom updateHotelRoom(@PathVariable(required = false) String id, @RequestBody HotelRoom room) {
        return roomService.updateHotelRoom(id, room);
    }
}
