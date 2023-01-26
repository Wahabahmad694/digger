package com.example.digger.state


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
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}


inline fun <T> networkBoundResource(
    crossinline query: () -> Flow<T>,
) = channelFlow {
    val data = query().first()
    send(Resource.Success(data))
}

inline fun <T> networkBoundResource(
    crossinline fetch: suspend () -> T
) = channelFlow {
    send(Resource.Loading(null))
    try {
        send(Resource.Success(fetch()))
    } catch (throwable: Throwable) {
        send(Resource.Error(throwable, null))
        throwable.printStackTrace()
    }
}

