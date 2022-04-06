package com.example.cryptocoins.domain

import com.example.cryptocoins.data.database.EntityMapper
import com.example.cryptocoins.data.database.entity.Exchange2Entity
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import com.example.cryptocoins.data.network.ResponseMapper
import com.example.cryptocoins.data.network.response.Exchange2Response
import com.example.cryptocoins.data.network.response.ExchangeResponse

data class Exchange2(
    val name: String,
    val url: String,
    val image: String,
    val twitter_handle: String?
): ResponseMapper<Exchange2Response>, EntityMapper<Exchange2Entity> {
    override fun toResponseModel(): Exchange2Response {
        return Exchange2Response (
            name = name,
            url = url,
            image = image,
            twitter_handle = twitter_handle
        )
    }

    override fun toEntityModel(): Exchange2Entity {
        return Exchange2Entity(
            name = name,
            url = url,
            image = image,
            twitter_handle = twitter_handle
        )
    }
}