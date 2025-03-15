package ru.hpclab.hl.module1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class HotelRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private RoomType type;

    @Column
    @NonNull
    private long costPerNight;

    public HotelRoom(long id) {
        this.id = id;
    }

    public HotelRoom() {

    }
}

