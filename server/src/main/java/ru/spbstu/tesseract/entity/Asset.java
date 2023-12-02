package ru.spbstu.tesseract.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "assets_id_seq")
    private Integer id;
    private String title;
    private String description;
    private Date releaseDatetime;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private Double interest;
    @OneToMany
    @JoinColumn(name = "asset_id")
    private List<Price> prices;
}
