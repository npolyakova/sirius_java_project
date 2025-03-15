package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.dto.GuestDto;
import ru.hpclab.hl.module1.service.GuestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/guests")
public class GuestController {
    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("")
    public List<GuestDto> getGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{id}")
    public Optional<GuestDto> getGuestById(@PathVariable String id) {
        return guestService.getGuestById(id);
    }

    @PostMapping("")
    public GuestDto saveGuest(@RequestBody GuestDto client) {
        return guestService.saveGuest(client);
    }

//    @PutMapping(value = "/{id}")
//    public Guest updateGuest(@PathVariable(required = false) String id, @RequestBody Guest guest) {
//        return guestService.updateGuest(id, guest);
//    }
}
