package ru.spbstu.tesseract.entity;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "diversifications")
@NoArgsConstructor
public class Diversification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "diversifications_id_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private ZonedDateTime createDatetime;

    @Column(name = "risk_type_id")
    @Enumerated(EnumType.ORDINAL)
    private RiskType riskType;

    private int amount;

    @OneToMany(mappedBy = "diversification", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<DiversificationAsset> diversificationAssetSet;

    public Diversification(
            User user,
            ZonedDateTime createDatetime,
            RiskType riskType,
            int amount,
            List<DiversificationAsset> diversificationsAssets)
    {
        this.user = user;
        this.createDatetime = createDatetime;
        this.riskType = riskType;
        this.amount = amount;
        for (DiversificationAsset diversificationAsset : diversificationsAssets) {
            diversificationAsset.setDiversification(this);
        }
        this.diversificationAssetSet = new HashSet<>(diversificationsAssets);
    }
}
