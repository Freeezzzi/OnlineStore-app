package ru.freeezzzi.coursework.onlinestore.data.repositories

import androidx.lifecycle.LiveData
import ru.freeezzzi.coursework.onlinestore.data.PrefsStorage
import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.data.network.models.LoginRequestDTO
import ru.freeezzzi.coursework.onlinestore.data.network.models.RegisterRequestDTO
import ru.freeezzzi.coursework.onlinestore.data.network.models.fromUser
import ru.freeezzzi.coursework.onlinestore.data.network.models.toUser
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.User
import ru.freeezzzi.coursework.onlinestore.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val prefsStorage: PrefsStorage,
    private val serverApi: ServerAPI
) : AuthRepository {

    override fun loadUser(): User? = prefsStorage.loadUser()

    override fun observeUser(): LiveData<User?> = prefsStorage.observeUser()

    override suspend fun login(
        username: String,
        password: String
    ): OperationResult<Unit, String?> =
        try {
            val user = serverApi.login(
                LoginRequestDTO(
                    username = username,
                    pwd = password
                )
            ).toUser()

            prefsStorage.saveToSharedPref(user)

            OperationResult.Success(Unit)
        } catch (e: Exception) {
            OperationResult.Error(e.message)
        }

    override suspend fun register(
        name: String,
        pwd: String,
        phone: String,
        email: String,
    ): OperationResult<Unit, String?> =
        try {
            val user = serverApi.register(
                RegisterRequestDTO(
                    pwd = pwd,
                    name = name,
                    email = email,
                    phone = phone
                )
            ).toUser()

            prefsStorage.saveToSharedPref(user)

            OperationResult.Success(Unit)
        } catch (e: Exception) {
            OperationResult.Error(e.message)
        }

    override fun logOut() =
        prefsStorage.saveToSharedPref(null)

    override fun saveUser(user: User) {
        prefsStorage.saveToSharedPref(user)
    }

    override suspend fun updateUserOnServer(user: User): OperationResult<Boolean, String?> =
            try {
                val result = serverApi.updateUser(
                        token = user.token,
                        userProfileDTO = fromUser(user)
                )

                prefsStorage.saveToSharedPref(user)

                OperationResult.Success(result)
            } catch (e: Exception) {
                OperationResult.Error(e.message)
            }



}
