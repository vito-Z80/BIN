package com.example.bin.app

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.bin.cardData
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request


//  https://kodlogs.net/325/no-network-security-config-specified-using-platform-default


private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
}
val cor = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)
val client = OkHttpClient()
val requestFalse = mutableStateOf(false)
val request = { bin: String, context: Context/*, f:()->Unit*/ ->
    cor.launch {
        cardData.value = withContext(coroutineContext) {
            try {
                val request = Request.Builder()
                    .url("https://lookup.binlist.net/${bin.trim()}")
                    .build()
                val resp = client.newCall(request).execute()
                val r = resp.body?.string()
                gson.fromJson(r, BinData::class.java)
            } catch (e: Exception) {
                null
            }
        }.also { requestFalse.value = it == null }

    }
}

