package tk.quietdev.level1.utils

import android.util.Log
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
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable.message ?: "error while data fetch", it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}

inline fun <ResultType, RequestType, FlowToObserve, FlowToConvert> networkBoundResource(
    crossinline firstQuery: () -> Flow<FlowToObserve>,
    crossinline secondQuery: (FlowToObserve) -> Flow<FlowToConvert>,
    crossinline convertToResult: (FlowToConvert) -> ResultType,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (FlowToObserve) -> Boolean = { true }
) = flow {
    val data = firstQuery().first()
    val flow = if (shouldFetch(data)) {
        val convertedData = convertToResult(secondQuery(data).first())
        emit(Resource.Loading(convertedData))
        try {
            saveFetchResult(fetch())
            firstQuery().map {
                Resource.Success(convertToResult(secondQuery(data).first())) }
        } catch (throwable: Throwable) {
            firstQuery().map { Resource.Error(throwable.message ?: "error while data fetch", convertToResult(secondQuery(data).first())) }
        }
    } else {
        firstQuery().map { Resource.Success(convertToResult(secondQuery(data).first())) }
    }
    emitAll(flow)
}


suspend inline fun <RequestType> networkBoundResource(
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) {
    try {
        Log.d("TAG", "networkBoundResource: ")
        saveFetchResult(fetch())
    } catch (throwable: Throwable) {
        Log.d("TAG", "networkBoundResource: EERR ${throwable.printStackTrace()}")
    }
}
