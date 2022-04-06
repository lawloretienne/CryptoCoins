package com.example.cryptocoins.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.dao.ExchangeDao
import com.example.cryptocoins.data.database.entity.CoinEntity
import com.example.cryptocoins.data.database.entity.ExchangeEntity
import com.example.cryptocoins.data.database.migrations.Migration1To2

@Database(
    entities = [CoinEntity::class, ExchangeEntity::class],
    version = 2)
abstract class CryptoCoinsDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
    abstract fun exchangeDao(): ExchangeDao

    companion object {
        @JvmField
        val MIGRATION_1_2 = Migration1To2()
    }
}