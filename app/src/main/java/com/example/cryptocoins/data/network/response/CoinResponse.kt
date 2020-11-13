package com.example.cryptocoins.data.network.response

import com.example.cryptocoins.data.database.EntityMapper
import com.example.cryptocoins.data.database.entity.CoinEntity
import com.example.cryptocoins.data.database.entity.DescriptionEntity
import com.example.cryptocoins.data.database.entity.ImageEntity
import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.domain.Description
import com.example.cryptocoins.domain.DomainMapper
import com.example.cryptocoins.domain.Image

data class CoinResponse(
    val id: String,
    val symbol: String,
    val name: String,
    val description: DescriptionResponse? = null,
    val image: ImageResponse? = null,
    val genesis_date: String? = "",
) : EntityMapper<CoinEntity>, DomainMapper<Coin> {
    override fun toEntityModel(): CoinEntity {
        return CoinEntity(
            id = id,
            symbol = symbol,
            name = name,
            description = description?.toEntityModel(),
            image = image?.toEntityModel(),
            genesisDate = genesis_date
        )
    }

    override fun toDomainModel(): Coin {
        return Coin(
            id = id,
            symbol = symbol,
            name = name,
            description = description?.toDomainModel(),
            image = image?.toDomainModel(),
            genesisDate = genesis_date
        )
    }
}

data class DescriptionResponse(
    val en: String
) : EntityMapper<DescriptionEntity>, DomainMapper<Description> {
    override fun toEntityModel(): DescriptionEntity {
        return DescriptionEntity(
            en = en
        )
    }

    override fun toDomainModel(): Description {
        return Description(
            en = en
        )
    }
}

data class ImageResponse(
    val thumb: String,
    val small: String,
    val large: String
) : EntityMapper<ImageEntity>, DomainMapper<Image> {
    override fun toEntityModel(): ImageEntity {
        return ImageEntity(
            thumb = thumb,
            small = small,
            large = large
        )
    }

    override fun toDomainModel(): Image {
        return Image(
            thumb = thumb,
            small = small,
            large = large
        )
    }
}
