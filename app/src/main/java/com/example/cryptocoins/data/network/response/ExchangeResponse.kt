package com.example.cryptocoins.data.network.response

import com.example.cryptocoins.data.database.EntityMapper
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import com.example.cryptocoins.domain.DomainMapper
import com.example.cryptocoins.domain.Exchange

data class ExchangeResponse(
    val id: String,
    val name: String,
    val url: String,
    val image: String
) : EntityMapper<ExchangeEntity>, DomainMapper<Exchange> {
    override fun toEntityModel(): ExchangeEntity {
        return ExchangeEntity(
            id = id,
            name = name,
            url = url,
            image = image
        )
    }

    override fun toDomainModel(): Exchange {
        return Exchange(
            id = id,
            name = name,
            url = url,
            image = image
        )
    }
}