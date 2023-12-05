package ru.spbstu.tesseract.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private Date createDatetime;
    @Column(name = "risk_type_id")
    @Enumerated(EnumType.ORDINAL)
    private RiskType riskType;
    private int amount;
    @OneToMany(mappedBy = "diversification", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<DiversificationAsset> diversificationAssetSet;

    public Diversification(
            User user,
            Date createDatetime,
            RiskType riskType,
            int amount,
            DiversificationAsset... diversificationsAssets) {
        this.user = user;
        this.createDatetime = createDatetime;
        this.riskType = riskType;
        this.amount = amount;
        for (DiversificationAsset diversificationAsset : diversificationsAssets) {
            diversificationAsset.setDiversification(this);
        }
        this.diversificationAssetSet = Stream.of(diversificationsAssets)
                .collect(Collectors.toSet());
    }
}
