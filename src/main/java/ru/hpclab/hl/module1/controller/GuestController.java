package ru.hpclab.hl.module1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.model.Guest;
import ru.hpclab.hl.module1.service.GuestService;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {
    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("")
    public List<Guest> getGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable String id) {
        return guestService.getGuestById(id);
    }

    @PostMapping("")
    public Guest saveGuest(@RequestBody Guest client) {
        return guestService.saveGuest(client);
    }

    @PutMapping(value = "/{id}")
    public Guest updateGuest(@PathVariable(required = false) String id, @RequestBody Guest guest) {
        return guestService.updateGuest(id, guest);
    }
}
