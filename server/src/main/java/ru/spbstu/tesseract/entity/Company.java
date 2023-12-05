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
    private Integer id;
    private String title;
    private String description;
    private Date foundationDatetime;
    private Long revenue;
    private Long profit;
    private Integer staff;
}
