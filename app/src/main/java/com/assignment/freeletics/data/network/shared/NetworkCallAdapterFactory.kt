package com.assignment.freeletics.data.network.shared

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        return if (isValidType(returnType)) {
            val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
            // the response type is ApiResponse and should be parameterized
            check(responseType is ParameterizedType) {
                "Response must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>"
            }

            val successBodyType = getParameterUpperBound(0, responseType)

            NetworkResponseAdapter<Any>(successBodyType)
        } else
            null
    }

    private fun isValidType(returnType: Type): Boolean {
        if (Call::class.java != getRawType(returnType)) {
            return false
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not ApiResponse then we can't handle this type, so we return null
        return getRawType(responseType) == NetworkResponse::class.java
    }
}
