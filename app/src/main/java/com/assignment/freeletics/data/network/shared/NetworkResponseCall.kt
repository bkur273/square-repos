package com.assignment.freeletics.data.network.shared

import okhttp3.Request
import okio.IOException
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NetworkResponseCall<ResultType : Any>(
    private val delegate: Call<ResultType>,
) : Call<NetworkResponse<ResultType>> {

    override fun enqueue(callback: Callback<NetworkResponse<ResultType>>) {
        delegate.enqueue(
            object : Callback<ResultType> {

                override fun onResponse(call: Call<ResultType>, response: Response<ResultType>) {
                    val body = response.body()
                    val code = response.code()
                    val error = response.errorBody()

                    if (response.isSuccessful) {
                        if (body != null) {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.Success(body))
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.SuccessNoBody)
                            )
                        }
                    } else {
                        val errorBody = error?.string() ?: ""
                        val errorText = JSONObject(errorBody).getString("message")
                        val errorType = JSONObject(errorBody).getString("code")
                        if (error != null && errorText.isNotEmpty()) {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.ApiError(
                                        errorText,
                                        code,
                                        NetworkErrorType.BadRequest
                                    )
                                )
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.UnknownError(
                                        EmptyErrorBodyException()
                                    )
                                )
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResultType>, throwable: Throwable) {
                    val networkResponse = when (throwable) {
                        is IOException -> NetworkResponse.NetworkError(throwable)
                        else -> NetworkResponse.UnknownError(throwable)
                    }
                    callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
                }
            }
        )
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<ResultType>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}