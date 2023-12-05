package ru.spbstu.tesseract.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            DiversificationAsset... diversificationsAssets)
    {
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

    public String convertCreateDatetimeToString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));
        return simpleDateFormat.format(createDatetime);
    }
}
