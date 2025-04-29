package ru.hpclab.hl.module1.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.util.Date;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "guestId")
    private Guest guest;

    @NonNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public Booking(long id, Guest guest, HotelRoom room, Date arrivalDate, Date leavingDate) {
        this.id = id;
        this.guest = guest;
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.leavingDate = leavingDate;
    }

    public Booking() {

    }
}
