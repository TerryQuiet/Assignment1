package tk.quietdev.level1.utils

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            delay(2222)
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            Log.d("TAG", "networkBoundResource: EERR ${throwable.printStackTrace()}")
            Log.d("TAG", "networkBoundResource: ${throwable.message}")
            query().map { Resource.Error(throwable.message ?: "error while data fetch", it) }
        }
    } else {
        Log.d("TAG", "networkBoundResource: SHOULD NEVER HAPPEN")
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}


inline fun <ResultType, RequestType> networkBoundResourceFetch(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) = flow {
        emit(Resource.Loading<ResultType>(null))
    try {
        saveFetchResult(fetch())
        emitAll(query().map { (Resource.Success(it)) })
    } catch (throwable: Throwable) {
        emitAll(query().map { (Resource.Error(throwable.message ?: "error while data fetch", it)) })
    }
}
