package com.example.cryptocoins.data.database.dao

import androidx.room.*
import com.example.cryptocoins.data.database.entity.CoinEntity

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin")
    fun getAllCoins(): List<CoinEntity>

    @Query("SELECT * FROM coin WHERE id = :id")
    fun findCoinById(id: String): CoinEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoins(coinEntities: List<CoinEntity>)

    @Delete
    fun deleteCoin(coinEntity: CoinEntity)
}
