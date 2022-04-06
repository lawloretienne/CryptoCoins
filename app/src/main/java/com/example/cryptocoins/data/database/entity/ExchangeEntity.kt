package com.example.cryptocoins.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptocoins.data.network.ResponseMapper
import com.example.cryptocoins.data.network.response.ExchangeResponse

@Entity(tableName = "exchange")
data class ExchangeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "image")
    val image: String
) : ResponseMapper<ExchangeResponse> {
    override fun toResponseModel(): ExchangeResponse {
        return ExchangeResponse(
            id = id,
            name = name,
            url = url,
            image = image
        )
    }
}