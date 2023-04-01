package com.v4victor.websocket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.v4victor.core.TOKEN
import com.v4victor.core.dto.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*


class WebSocketClient(
    private val stocks: List<String>,
    private val coroutineScope: CoroutineScope
) {
    private val okHttpClient = OkHttpClient()
    private lateinit var ws: WebSocket
    private val flow = MutableStateFlow(Result(listOf(Data())))
    private val liveData = MutableLiveData(Result(listOf()))

    val _flow: StateFlow<Result> = flow
    val _liveData: LiveData<Result> = liveData
    val json = Json { ignoreUnknownKeys = true }



    fun start() {
        val request = Request.Builder()
            .url(WEB_SOCKET_URL)
            .build()
        ws = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//                unsubscribe(webSocket)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "onError: ${t.message}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                coroutineScope.launch {
                    withContext(Dispatchers.IO)
                    {
                        try {
                            Log.e(TAG, "Thread: " + Thread.currentThread().name)
                            val result = json.decodeFromString<Result>(text)
                            flow.emit(result)
//                            liveData.value = result
                        } catch (e: SerializationException) {
                            Log.e(TAG, "SerializationException ${e.message}")
                        }
                    }
                }
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.e(TAG, "onOpen ${stocks.joinToString { "$it " }}")
                subscribe(webSocket)
            }
        })
    }

    fun addTicket(ticket: String) {
        ws.send("{\"type\":\"subscribe\",\"symbol\":\"$ticket\"}")
    }

    fun subscribe(webSocket: WebSocket) {
        stocks.forEach { webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"$it\"}") }
    }

    fun unsubscribe(webSocket: WebSocket) {
        webSocket.send(
            "{\n" +
                    "    \"type\": \"unsubscribe\",\n" +
                    "    \"channels\": [\"ticker\"]\n" +
                    "}"
        )
    }


    companion object {
        const val WEB_SOCKET_URL = "wss://ws.finnhub.io?token=$TOKEN"
        const val TAG = "WebSocket"
    }
}