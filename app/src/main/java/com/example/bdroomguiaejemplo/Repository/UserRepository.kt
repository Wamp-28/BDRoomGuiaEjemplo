package com.example.bdroomguiaejemplo.Repository

import com.example.bdroomguiaejemplo.DAO.UserDao
import com.example.bdroomguiaejemplo.Model.User


class UserRepository(private val userDao: UserDao) {
    suspend fun insertar(user: User) {
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun deleteById(userId: Int): Int {
        return userDao.deleteById(userId) // Llama al método deleteById del DAO
    }


    suspend fun delete(user: User) {
        userDao.delete(user) // Llamada al método delete del DAO
    }


}