package cn.juerwhang.jurtiles.network

import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class ClientUtilTest {
    @KtorExperimentalAPI
    @Test
    fun doGetTest() {
        runBlocking {
            val response = doGet<String>("http://www.baidu.com", "ie" to "utf-8", "wd" to "ktor")
            assert(response.contains("Ktor"))
        }
    }
}
