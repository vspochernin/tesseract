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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "portfolios_id_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private ZonedDateTime createDatetime;

    @Column(name = "risk_type_id")
    @Enumerated(EnumType.ORDINAL)
    private RiskType riskType;

    private long amount;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<PortfolioAsset> portfolioAssetSet;

    public Portfolio(
            User user,
            ZonedDateTime createDatetime,
            RiskType riskType,
            long amount,
            List<PortfolioAsset> portfoliosAssets)
    {
        this.user = user;
        this.createDatetime = createDatetime;
        this.riskType = riskType;
        this.amount = amount;
        for (PortfolioAsset portfolioAsset : portfoliosAssets) {
            portfolioAsset.setPortfolio(this);
        }
        this.portfolioAssetSet = new HashSet<>(portfoliosAssets);
    }

    public long getCurrentAmount() {
        return portfolioAssetSet.stream()
                .map(portfolioAsset -> {
                    long count = portfolioAsset.getCount();
                    long currentAssetPrice = portfolioAsset.getAsset().getCurrentAssetPrice();

                    return currentAssetPrice * count;
                })
                .mapToLong(Long::valueOf)
                .sum();
    }
}
