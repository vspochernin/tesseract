package ru.spbstu.tesseract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "diversifications_assets")
public class DiversificationAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "diversifications_assets_id_seq")
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "diversification_id")
    @JsonIgnore
    private Diversification diversification;

    @ManyToOne()
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private Integer count;

    public DiversificationAsset(Asset asset, Integer count) {
        this.asset = asset;
        this.count = count;
    }
}
