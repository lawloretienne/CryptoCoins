package com.example.cryptocoins.domain

import com.example.cryptocoins.data.database.EntityMapper
import com.example.cryptocoins.data.database.entity.CoinEntity
import com.example.cryptocoins.data.database.entity.DescriptionEntity
import com.example.cryptocoins.data.database.entity.ImageEntity
import com.example.cryptocoins.data.network.ResponseMapper
import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.response.DescriptionResponse
import com.example.cryptocoins.data.network.response.ImageResponse

data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val description: Description? = null,
    val image: Image? = null,
    val genesisDate: String? = ""
) : ResponseMapper<CoinResponse>, EntityMapper<CoinEntity> {
    override fun toResponseModel(): CoinResponse {
        return CoinResponse (
            id = id,
            symbol = symbol,
            name = name,
            description = description?.toResponseModel(),
            image = image?.toResponseModel(),
            genesis_date = genesisDate
        )
    }

    override fun toEntityModel(): CoinEntity {
        return CoinEntity(
            id = id,
            symbol = symbol,
            name = name,
            description = description?.toEntityModel(),
            image = image?.toEntityModel(),
            genesisDate = genesisDate
        )
    }
}

data class Description(
    val en: String
): ResponseMapper<DescriptionResponse>, EntityMapper<DescriptionEntity> {
    override fun toResponseModel(): DescriptionResponse {
        return DescriptionResponse (
            en = en
        )
    }

    override fun toEntityModel(): DescriptionEntity {
        return DescriptionEntity(
            en = en
        )
    }
}

data class Image(
    val thumb: String,
    val small: String,
    val large: String
): ResponseMapper<ImageResponse>, EntityMapper<ImageEntity> {
    override fun toResponseModel(): ImageResponse {
        return ImageResponse (
            thumb = thumb,
            small = small,
            large = large
        )
    }

    override fun toEntityModel(): ImageEntity {
        return ImageEntity(
            thumb = thumb,
            small = small,
            large = large
        )
    }
}