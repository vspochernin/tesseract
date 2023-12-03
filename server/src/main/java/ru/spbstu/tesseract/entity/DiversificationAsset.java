package ru.spbstu.tesseract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "diversifications_assets")
public class DiversificationAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "diversifications_assets_id_seq")
    private Integer id;

    @ManyToOne(/*fetch = FetchType.LAZY, optional = false*/)
    @JoinColumn(name = "diversification_id")
    @JsonIgnore
    private Diversification diversification;

    @ManyToOne(/*fetch = FetchType.LAZY, optional = false*/)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private Integer count;

    public DiversificationAsset(Asset asset, Integer count) {
        this.asset = asset;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiversificationAsset that = (DiversificationAsset) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
