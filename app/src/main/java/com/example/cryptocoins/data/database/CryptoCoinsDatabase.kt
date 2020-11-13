package com.example.cryptocoins.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptocoins.data.database.dao.CoinDao
import com.example.cryptocoins.data.database.entity.CoinEntity

@Database(
    entities = [CoinEntity::class],
    version = 1)
abstract class CryptoCoinsDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}