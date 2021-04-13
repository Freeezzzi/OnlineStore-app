package ru.freeezzzi.coursework.onlinestore.data.repositories

import androidx.lifecycle.LiveData
import ru.freeezzzi.coursework.onlinestore.data.PrefsStorage
import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
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
            /*val user = serverApi.login(
                LoginRequestDTO(
                    username = username,
                    pwd = password
                )
            ).toUser()

            prefsStorage.saveToSharedPref(user)*/

            OperationResult.Success(Unit)

        } catch (e: Exception) {
            OperationResult.Error(e.message)
        }


    override suspend fun register(
        username: String,
        password: String,
        name: String,
        isMentor: Boolean
    ): OperationResult<Unit, String?> =
        try {
            /*val user = serverApi.register(
                RegisterRequestDTO(
                    username = username,
                    pwd = password,
                    name = name,
                    isMentor = isMentor
                )
            ).toUser()

            prefsStorage.saveToSharedPref(user)*/

            OperationResult.Success(Unit)

        } catch (e: Exception) {
            OperationResult.Error(e.message)
        }


    override fun logOut() =
        prefsStorage.saveToSharedPref(null)
}