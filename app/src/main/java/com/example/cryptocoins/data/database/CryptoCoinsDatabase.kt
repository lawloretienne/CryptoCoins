package com.example.cryptocoins.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.dao.Exchange2Dao
import com.example.cryptocoins.data.database.dao.ExchangeDao
import com.example.cryptocoins.data.database.entity.CoinEntity
import com.example.cryptocoins.data.database.entity.Exchange2Entity
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import com.example.cryptocoins.data.database.migrations.Migration1To2
import com.example.cryptocoins.data.database.migrations.Migration2To3

@Database(
    entities = [CoinEntity::class, ExchangeEntity::class, Exchange2Entity::class],
    version = 3)
abstract class CryptoCoinsDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun exchangeDao(): ExchangeDao
    abstract fun exchange2Dao(): Exchange2Dao

    companion object {
        @JvmField
        val MIGRATION_1_2 = Migration1To2()
        @JvmField
        val MIGRATION_2_3 = Migration2To3()
    }
}