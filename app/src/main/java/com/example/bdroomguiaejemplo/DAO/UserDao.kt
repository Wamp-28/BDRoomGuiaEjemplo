package com.example.bdroomguiaejemplo.DAO


import androidx.room.*
import com.example.bdroomguiaejemplo.Model.User


@Dao
interface UserDao {

    //  @Query("INSERT INTO users (nombre, apellido, edad) VALUES (:nombre, :apellido, :edad)")

    // Suspend evitar que la aplica falle cuando se realizan las peticiones al realizar operaciones asincrona

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Revision de conflictos entre entregistros
    suspend fun insert(user: User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>


    @Update
    suspend fun update(user: User)


    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteById(userId: Int): Int


    @Delete
    suspend fun delete(user: User)




}
