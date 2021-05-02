package ru.freeezzzi.coursework.onlinestore.domain.repositories

import androidx.lifecycle.LiveData
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.User

interface AuthRepository {
    fun loadUser(): User?

    suspend fun login(username: String, password: String): OperationResult<Unit, String?>

    suspend fun register(
        name: String,
        pwd: String,
        phone: String,
        email: String
    ): OperationResult<Unit, String?>

    fun observeUser(): LiveData<User?>

    fun logOut()

    fun saveUser(user: User)
}
