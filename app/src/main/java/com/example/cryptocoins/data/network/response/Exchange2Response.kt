package com.example.cryptocoins.data.network.response

import com.example.cryptocoins.data.database.EntityMapper
import com.example.cryptocoins.data.database.entity.Exchange2Entity
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import com.example.cryptocoins.domain.DomainMapper
import com.example.cryptocoins.domain.Exchange
import com.example.cryptocoins.domain.Exchange2

data class Exchange2Response(
    val name: String,
    val url: String,
    val image: String,
    val twitter_handle: String?
) : EntityMapper<Exchange2Entity>, DomainMapper<Exchange2> {
    override fun toEntityModel(): Exchange2Entity {
        return Exchange2Entity(
            name = name,
            url = url,
            image = image,
            twitter_handle = twitter_handle
        )
    }

    override fun toDomainModel(): Exchange2 {
        return Exchange2(
            name = name,
            url = url,
            image = image,
            twitter_handle = twitter_handle
        )
    }
}