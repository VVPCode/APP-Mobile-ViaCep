package com.example.cep.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Endereco::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun enderecoDao(): EnderecoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cep_database"
                )
                    .fallbackToDestructiveMigration() // <- aqui é onde forçamos recriação do banco em mudanças de versão
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
