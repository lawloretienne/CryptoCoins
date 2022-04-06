package com.example.cryptocoins.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptocoins.data.network.ResponseMapper
import com.example.cryptocoins.data.network.response.Exchange2Response
import com.example.cryptocoins.data.network.response.ExchangeResponse

@Entity(tableName = "exchange_2")
data class Exchange2Entity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "twitter_handle")
    val twitter_handle: String?
) : ResponseMapper<Exchange2Response> {
    override fun toResponseModel(): Exchange2Response {
        return Exchange2Response(
            name = name,
            url = url,
            image = image,
            twitter_handle = twitter_handle
        )
    }
}