package ru.freeezzzi.coursework.onlinestore.data.repositories

import ru.freeezzzi.coursework.onlinestore.data.network.ServerAPI
import ru.freeezzzi.coursework.onlinestore.domain.OperationResult
import ru.freeezzzi.coursework.onlinestore.domain.models.Category
import ru.freeezzzi.coursework.onlinestore.domain.repositories.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val serverAPI: ServerAPI,
    private val authRepositoryImpl: AuthRepositoryImpl
) : CategoriesRepository {
    override suspend fun getCategories(): OperationResult<List<Category>, String?> =
        try {
            val categories = serverAPI.getAllCategories(authRepositoryImpl.loadUser()!!.token).map {
                it.toCategory()
            }

            OperationResult.Success(categories)
        } catch (e: Throwable) {
            OperationResult.Error(e.message)
        }
}
