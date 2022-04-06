package com.example.cryptocoins.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// CryptoCoinsDatabase_Impl contains migration details

class Migration2To3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("CREATE TABLE IF NOT EXISTS `exchange_2` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `url` TEXT NOT NULL, `image` TEXT NOT NULL, `twitter_handle` TEXT, PRIMARY KEY(`name`))")

    }

}