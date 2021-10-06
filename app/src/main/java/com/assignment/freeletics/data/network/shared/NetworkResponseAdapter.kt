package com.assignment.freeletics.data.network.shared

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<ResultType : Any>(
    private val successType: Type,
) : CallAdapter<ResultType, Call<NetworkResponse<ResultType>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<ResultType>): Call<NetworkResponse<ResultType>> {
        return NetworkResponseCall(call)
    }
}
