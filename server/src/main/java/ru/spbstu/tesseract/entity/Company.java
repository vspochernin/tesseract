package ru.spbstu.tesseract.entity;

import java.time.Duration;
import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public long getYearsSinceFoundation() {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        return Duration.between(foundationDatetime, currentDateTime).toDays() / 365;
    }
}
