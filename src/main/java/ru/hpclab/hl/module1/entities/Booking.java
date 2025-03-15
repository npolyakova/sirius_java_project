package ru.hpclab.hl.module1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

import java.util.Date;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "guestId")
    private Guest guest;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "roomId")
    private HotelRoom room;

    @Column
    @NonNull
    private Date arrivalDate;

    @Column
    @NonNull
    private Date leavingDate;

    @Column
    private double cost;

    public Booking(long id, Guest guest, Date arrivalDate, Date leavingDate) {
        this.id = id;
        this.guest = guest;
        this.arrivalDate = arrivalDate;
        this.leavingDate = leavingDate;
    }

    public Booking() {

    }
}
