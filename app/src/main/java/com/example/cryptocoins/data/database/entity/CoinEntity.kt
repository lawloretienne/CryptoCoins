package com.example.cryptocoins.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptocoins.data.network.ResponseMapper
import com.example.cryptocoins.data.network.response.CoinResponse
import com.example.cryptocoins.data.network.response.DescriptionResponse
import com.example.cryptocoins.data.network.response.ImageResponse


@Entity(tableName = "coin")
data class CoinEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "name")
    val name: String,
    @Embedded(prefix = "description_")
    val description: DescriptionEntity? = null,
    @Embedded(prefix = "image_")
    val image: ImageEntity? = null,
    @ColumnInfo(name = "genesis_date")
    val genesisDate: String? = ""
) : ResponseMapper<CoinResponse> {
    override fun toResponseModel(): CoinResponse {
        return CoinResponse(
            id = id,
            symbol = symbol,
            name = name,
            description = description?.toResponseModel(),
            image = image?.toResponseModel(),
            genesis_date = genesisDate
        )
    }
}


data class DescriptionEntity(
    @ColumnInfo(name = "en")
    val en: String
) : ResponseMapper<DescriptionResponse> {
    override fun toResponseModel(): DescriptionResponse {
        return DescriptionResponse(
            en = en
        )
    }
}

data class ImageEntity(
    @ColumnInfo(name = "thumb")
    val thumb: String,
    @ColumnInfo(name = "small")
    val small: String,
    @ColumnInfo(name = "large")
    val large: String
) : ResponseMapper<ImageResponse> {
    override fun toResponseModel(): ImageResponse {
        return ImageResponse(
            thumb = thumb,
            small = small,
            large = large
        )
    }
}
