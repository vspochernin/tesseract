package ru.spbstu.tesseract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    private int id;

    @ManyToOne()
    @JoinColumn(name = "diversification_id")
    @JsonIgnore
    private Diversification diversification;

    @ManyToOne()
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private long count;

    public DiversificationAsset(Asset asset, int count) {
        this.asset = asset;
        this.count = count;
    }
}
