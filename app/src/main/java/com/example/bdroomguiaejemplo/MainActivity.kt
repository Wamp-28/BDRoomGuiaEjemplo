package com.example.bdroomguiaejemplo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bdroomguiaejemplo.DAO.UserDao
import com.example.bdroomguiaejemplo.Database.UserDatabase
import com.example.bdroomguiaejemplo.Repository.UserRepository
import com.example.bdroomguiaejemplo.Screen.UserApp

class MainActivity : ComponentActivity() {

// lateinit - evita el nulo ya que la variable se debe inicializar mas adelante

    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository // Agregar UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = UserDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userRepository = UserRepository(userDao) // Inicializa UserRepository

        enableEdgeToEdge()
        setContent {

            UserApp(userRepository) // Pasa UserRepository a UserApp
        }
    }
}

