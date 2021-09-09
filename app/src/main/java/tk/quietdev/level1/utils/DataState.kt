package tk.quietdev.level1.utils

sealed class DataState<out R> {

    class Success<out T>(val data: T) : DataState<T>()
    class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}