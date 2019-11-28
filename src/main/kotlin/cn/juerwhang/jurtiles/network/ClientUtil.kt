package cn.juerwhang.jurtiles.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.util.KtorExperimentalAPI
import java.lang.Exception


@KtorExperimentalAPI
val client: HttpClient by lazy {
    HttpClient(CIO) {
        engine {
            requestTimeout = 10000
            threadsCount = 4
        }
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doGet(url: String, vararg params: Pair<String, String>): T {
    return client.get(url) {
        params.forEach { parameter(it.first, it.second) }
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doPost(url: String, body: Any): T {
    return client.post(url) {
        this.body = body
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doPut(url: String, body: Any): T {
    return client.put(url) {
        this.body = body
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doDelete(url: String, body: Any): T {
    return client.delete(url) {
        this.body = body
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doSafeGet(url: String, vararg params: Pair<String, String>, handler: Exception.() -> T?): T? {
    return try {
        doGet(url, *params)
    } catch (error: Exception) {
        error.handler()
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doSafePost(url: String, body: Any, handler: Exception.() -> T?): T? {
    return try {
        client.post(url) { this.body = body }
    } catch (error: Exception) {
        error.handler()
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doSafePut(url: String, body: Any, handler: Exception.() -> T?): T? {
    return try {
        client.put(url) { this.body = body }
    } catch (error: Exception) {
        error.handler()
    }
}

@KtorExperimentalAPI
suspend inline fun <reified T> doSafeDelete(url: String, body: Any, handler: Exception.() -> T?): T? {
    return try {
        client.delete(url) { this.body = body }
    } catch (error: Exception) {
        error.handler()
    }
}
