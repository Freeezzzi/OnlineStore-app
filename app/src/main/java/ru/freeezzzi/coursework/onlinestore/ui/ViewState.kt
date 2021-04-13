package ru.freeezzzi.coursework.onlinestore.ui

/**
 * Describes state of the view at any
 * point of time.
 */
sealed class ViewState<out S : Any?, out E : Any?> {

    data class Success<out S : Any?>(val result: S) : ViewState<S, Nothing>()

    object Loading : ViewState<Nothing, Nothing>()

    data class Error<out S : Any?, out E : Any?>(val oldvalue: S, val result: E) : ViewState<S, E>()

    companion object {
        /**
         * Creates [ViewState] object with [Success] state and [data].
         */
        fun <S> success(data: S) = Success(data)

        /**
         * Creates [ViewState] object with [Loading] state to notify
         * the UI to showing loading.
         */
        fun loading() = Loading

        /**
         * Creates [ViewState] object with [Error] state [message] and [oldValue].
         */
        fun <S, E> error(oldValue: S, message: E) = Error(oldValue, message)
    }
}