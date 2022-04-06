package com.example.cryptocoins.domain

import com.example.cryptocoins.data.database.EntityMapper
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import com.example.cryptocoins.data.network.ResponseMapper
import com.example.cryptocoins.data.network.response.ExchangeResponse

data class Exchange(
    val id: String,
    val name: String,
    val url: String,
    val image: String
): ResponseMapper<ExchangeResponse>, EntityMapper<ExchangeEntity> {
    override fun toResponseModel(): ExchangeResponse {
        return ExchangeResponse (
            id = id,
            name = name,
            url = url,
            image = image
        )
    }

    override fun toEntityModel(): ExchangeEntity {
        return ExchangeEntity(
            id = id,
            name = name,
            url = url,
            image = image
        )
    }
}