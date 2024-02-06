package ru.spbstu.tesseract.entity;

import java.time.Duration;
import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "operators")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "operators_id_seq")
    private int id;

    private String title;
    private ZonedDateTime inclusionDatetime;

    public long getDaysSinceInsertion() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        return Duration.between(inclusionDatetime, currentDateTime).toDays();
    }
}
