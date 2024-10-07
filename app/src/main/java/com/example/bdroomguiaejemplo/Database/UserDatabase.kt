package com.example.bdroomguiaejemplo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bdroomguiaejemplo.DAO.UserDao
import com.example.bdroomguiaejemplo.Model.User


// Abstract para evitar crear nuevas instancias de la BD y room gestiona la creacion de la BD automaticamente

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // Compain Object Se usa para definir miembros estaticos en kotlin
    // Volatile permite que cualquier hilo que acceda a la variable tenga la version mas actualizada

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // Permite crear la instancia de la base de datos

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
