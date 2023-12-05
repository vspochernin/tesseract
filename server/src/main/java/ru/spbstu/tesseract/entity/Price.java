package ru.spbstu.tesseract.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "assets_id_seq")
    private int id;
    private int price;
    private Date setDatetime;
}
