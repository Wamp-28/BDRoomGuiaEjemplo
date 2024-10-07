package com.example.bdroomguiaejemplo.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bdroomguiaejemplo.DAO.UserDao
import com.example.bdroomguiaejemplo.Model.User
import com.example.bdroomguiaejemplo.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun UserApp(userRepository: UserRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val user = User(
                    nombre = nombre,
                    apellido = apellido,
                    edad = edad.toIntOrNull() ?: 0
                )
                // Corrutina para insertar el registro
                scope.launch {
                    withContext(Dispatchers.IO) {
                        userRepository.insertar(user)
                    }
                    Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Registrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        var users by remember { mutableStateOf(listOf<User>()) }

        Button(
            onClick = {
                scope.launch {
                    users = withContext(Dispatchers.IO) {
                        userRepository.getAllUsers()
                    }
                }
            }
        ) {
            Text("Listar")
        }


        Spacer(modifier = Modifier.height(16.dp))

        var idDelete by remember { mutableStateOf("") }

        TextField(
            value = idDelete,
            onValueChange = { idDelete = it },
            label = { Text("ID Eliminar") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val id = idDelete.toIntOrNull()
                if (id != null) {
                    // Inicia una coroutine para eliminar el usuario
                    scope.launch {
                        val deletedCount: Int
                        withContext(Dispatchers.IO) {
                            deletedCount = userRepository.deleteById(id)
                        }

                        // Mostrar el Toast de la eliminación
                        if (deletedCount > 0) {
                            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Usuario no existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // ID no es válido
                    Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Eliminar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            users.forEach { user ->
                Text("${user.id} ${user.nombre} ${user.apellido} ${user.edad}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


