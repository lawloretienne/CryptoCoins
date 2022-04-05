package com.example.cryptocoins.data.database.dao

import androidx.room.*
import com.example.cryptocoins.data.database.entity.CoinEntity

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    suspend fun getAllCoins(): List<CoinEntity>

    @Query("SELECT * FROM coin WHERE id = :id")
    suspend fun findCoinById(id: String): CoinEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coinEntities: List<CoinEntity>)

    @Delete
    suspend fun deleteCoin(coinEntity: CoinEntity)
}
