package ru.spbstu.tesseract.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    private Date foundationDatetime;
    private long revenue;
    private long profit;
    private int staff;
}
