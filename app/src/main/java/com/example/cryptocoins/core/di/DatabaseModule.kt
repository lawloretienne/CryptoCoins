package com.example.cryptocoins.core.di

import android.content.Context
import androidx.room.Room
import com.example.cryptocoins.data.database.CryptoCoinsDatabase
import com.example.cryptocoins.data.database.dao.CoinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    fun provideCryptoCoinsDatabase(@ApplicationContext context: Context): CryptoCoinsDatabase {
        return Room.databaseBuilder(context, CryptoCoinsDatabase::class.java, "CryptoCoins.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCoinDao(cryptoCoinsDatabase: CryptoCoinsDatabase): CoinDao {
        return cryptoCoinsDatabase.coinDao()
    }
}