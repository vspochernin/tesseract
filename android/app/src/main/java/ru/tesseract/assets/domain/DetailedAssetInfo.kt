package ru.tesseract.assets.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ru.tesseract.portfolios.domain.RiskLevel

@Serializable(with = DetailedAssetInfoSerializer::class)
data class DetailedAssetInfo(
    val generalInfo: GeneralAssetInfo,
    val description: String,
    val companyDescription: String,
    val operatorTitle: String,
    val riskLevel: RiskLevel,
)

@Serializable
private class DetailedAssetInfoSurrogate(
    @SerialName("assetId")
    val id: Int,
    @SerialName("assetTitle")
    val title: String,
    @SerialName("companyTitle")
    val companyTitle: String,
    @SerialName("assetPrice")
    val price: Long,
    @SerialName("assetPriceDiff")
    val priceDiff: Long,
    @SerialName("favouriteStatus")
    val isFavorite: Boolean,
    @SerialName("assetDescription")
    val description: String,
    @SerialName("companyDescription")
    val companyDescription: String,
    @SerialName("operatorTitle")
    val operatorTitle: String,
    @SerialName("riskTypeId")
    val riskLevelOrdinal: Int,
)

object DetailedAssetInfoSerializer : KSerializer<DetailedAssetInfo> {
    override val descriptor = DetailedAssetInfoSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: DetailedAssetInfo) {
        val surrogate = DetailedAssetInfoSurrogate(
            id = value.generalInfo.id,
            title = value.generalInfo.title,
            companyTitle = value.generalInfo.companyTitle,
            price = value.generalInfo.price,
            priceDiff = value.generalInfo.priceDiff,
            isFavorite = value.generalInfo.isFavorite,
            description = value.description,
            companyDescription = value.companyDescription,
            operatorTitle = value.operatorTitle,
            riskLevelOrdinal = value.riskLevel.ordinal,
        )
        encoder.encodeSerializableValue(DetailedAssetInfoSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): DetailedAssetInfo {
        val surrogate = decoder.decodeSerializableValue(DetailedAssetInfoSurrogate.serializer())
        return DetailedAssetInfo(
            generalInfo = GeneralAssetInfo(
                id = surrogate.id,
                title = surrogate.title,
                companyTitle = surrogate.companyTitle,
                price = surrogate.price,
                priceDiff = surrogate.priceDiff,
                isFavorite = surrogate.isFavorite,
            ),
            description = surrogate.description,
            companyDescription = surrogate.companyDescription,
            operatorTitle = surrogate.operatorTitle,
            riskLevel = RiskLevel.entries[surrogate.riskLevelOrdinal],
        )
    }
}
