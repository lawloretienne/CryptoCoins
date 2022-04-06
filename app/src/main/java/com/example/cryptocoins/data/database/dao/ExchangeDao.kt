package com.example.cryptocoins.data.database.dao

import androidx.room.*
import com.example.cryptocoins.data.database.entity.ExchangeEntity

@Dao
interface ExchangeDao {
    @Query("SELECT * FROM exchange")
    suspend fun getAllExchanges(): List<ExchangeEntity>

    @Query("SELECT * FROM exchange WHERE id = :id")
    suspend fun findExchangeById(id: String): ExchangeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchanges(exchangeEntities: List<ExchangeEntity>)

    @Delete
    suspend fun deleteExchange(exchangeEntity: ExchangeEntity)
}