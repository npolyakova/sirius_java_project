package ru.hpclab.hl.module1.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hpclab.hl.module1.entities.Guest;
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
    public List<Guest> getGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{id}")
    public Optional<Guest> getGuestById(@PathVariable String id) {
        return guestService.getGuestById(id);
    }

    @PostMapping("")
    public Guest saveGuest(@RequestBody Guest client) {
        return guestService.saveGuest(client);
    }

    @PutMapping("")
    public Guest updateGuest(@RequestBody Guest client) {
        return guestService.saveGuest(client);
    }

    @ApiIgnore
    @DeleteMapping("")
    public void deleteAllGuest() {
        guestService.deleteAll();
    }

}
