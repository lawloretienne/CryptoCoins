package com.example.cryptocoins.fakers


import com.example.cryptocoins.domain.Coin
import com.example.cryptocoins.domain.Description
import com.example.cryptocoins.domain.Image
import com.example.cryptocoins.fakers.BaseFaker.Companion.Fake.faker

object CoinFaker {

    fun basic() = Coin(
        faker.idNumber().valid(),
        faker.lorem().word(),
        faker.lorem().word(),
        DescriptionFaker.basic(),
        ImageFaker.basic(),
        faker.date().toString()
    )

    fun list() = BaseFaker.list { basic() }

}

object DescriptionFaker {

    fun basic() = Description(
        faker.lorem().sentence()
    )

    fun list() = BaseFaker.list { basic() }

}

object ImageFaker {

    fun basic() = Image(
        faker.internet().url(),
        faker.internet().url(),
        faker.internet().url()
    )

    fun list() = BaseFaker.list { basic() }

}