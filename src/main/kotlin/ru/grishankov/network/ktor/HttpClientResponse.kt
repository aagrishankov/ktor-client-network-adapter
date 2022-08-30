package ru.grishankov.network.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

/**
 * Response with error handling
 */
suspend inline fun <reified Data : Any, reified Failure : Any> HttpResponse.handleErrors(): Response<Data, Failure> {
    return if (status.isSuccess()) {
        Response.Ok(body(), status.value)
    } else {
        Response.ApiError(body(), status.value)
    }
}

/**
 * GET Response with error handling
 *
 * @param url URL
 * @param block optional get [HttpRequestBuilder] to further customize request
 */
suspend inline fun <reified Data : Any, reified Failure : Any> HttpClient.getResponse(
    url: String, block: HttpRequestBuilder.() -> Unit = {}
): Response<Data, Failure> {
    return try {
        get(url, block).handleErrors()
    } catch (t: Throwable) {
        when (t) {
            is IOException -> Response.NetworkError(t)
            else -> Response.UnknownError(t)
        }
    }
}

/**
 * POST Response with error handling
 *
 * @param url URL
 * @param block optional get [HttpRequestBuilder] to further customize request
 */
suspend inline fun <reified Data : Any, reified Failure : Any> HttpClient.postResponse(
    url: String, block: HttpRequestBuilder.() -> Unit = {}
): Response<Data, Failure> {
    return try {
        post(url, block).handleErrors<Data, Failure>()
    } catch (t: Throwable) {
        when (t) {
            is IOException -> Response.NetworkError(t)
            else -> Response.UnknownError(t)
        }
    }
}

/**
 * PUT Response with error handling
 *
 * @param url URL
 * @param block optional get [HttpRequestBuilder] to further customize request
 */
suspend inline fun <reified Data : Any, reified Failure : Any> HttpClient.putResponse(
    url: String, block: HttpRequestBuilder.() -> Unit = {}
): Response<Data, Failure> {
    return try {
        put(url, block).handleErrors()
    } catch (t: Throwable) {
        when (t) {
            is IOException -> Response.NetworkError(t)
            else -> Response.UnknownError(t)
        }
    }
}

/**
 * DELETE Response with error handling
 *
 * @param url URL
 * @param block optional get [HttpRequestBuilder] to further customize request
 */
suspend inline fun <reified Data : Any, reified Failure : Any> HttpClient.deleteResponse(
    url: String, block: HttpRequestBuilder.() -> Unit = {}
): Response<Data, Failure> {
    return try {
        delete(url, block).handleErrors()
    } catch (t: Throwable) {
        when (t) {
            is IOException -> Response.NetworkError(t)
            else -> Response.UnknownError(t)
        }
    }
}