package com.example.cryptocoins.data.database.dao

import androidx.room.*
import com.example.cryptocoins.data.database.entity.Exchange2Entity
import com.example.cryptocoins.data.database.entity.ExchangeEntity

@Dao
interface Exchange2Dao {
    @Query("SELECT * FROM exchange_2")
    suspend fun getAllExchanges(): List<Exchange2Entity>

    @Query("SELECT * FROM exchange_2 WHERE name = :name")
    suspend fun findExchangeByName(name: String): Exchange2Entity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchanges(exchangeEntities: List<Exchange2Entity>)

    @Delete
    suspend fun deleteExchange(exchangeEntity: Exchange2Entity)
}