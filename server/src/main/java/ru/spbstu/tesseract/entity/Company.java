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
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "companies_id_seq")
    private int id;

    private String title;
    private String description;
    private ZonedDateTime foundationDatetime;
    private long revenue;
    private long profit;
    private int staff;

    public double getCompanyAge() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        Duration duration = Duration.between(foundationDatetime, currentDateTime);
        return duration.toDays() / 365.0;
    }
}
