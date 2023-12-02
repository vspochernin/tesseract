package ru.spbstu.tesseract.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.spbstu.tesseract.auth.user.User;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "diversifications")
public class Diversification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "diversifications_id_seq")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Date createDatetime;
    @Column(name = "risk_id")
    @Enumerated(EnumType.ORDINAL)
    private RiskType riskType;
    private Integer amount;
    @ManyToMany
    @JoinTable(
            name = "diversifications_assets",
            joinColumns = @JoinColumn(name = "diversification_id"),
            inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    private List<Asset> assets;
}
