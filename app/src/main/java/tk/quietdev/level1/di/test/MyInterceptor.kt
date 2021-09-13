package tk.quietdev.level1.di.test

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import tk.quietdev.level1.utils.ext.fixJson
import java.nio.charset.Charset


class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val source = response.body?.source()
        source?.let {
            it.request(Long.MAX_VALUE)
            val buffer: Buffer = source.buffer
            val responseBodyString: String = buffer.clone().readString(Charset.forName("UTF-8"))
            Log.d("TAG", "intercept pre fix     : $responseBodyString")
            val fixed = responseBodyString.fixJson()
            Log.d("TAG", "intercept after fix   : $fixed")
            val contentType = response.body?.contentType()
            val body = fixed.toResponseBody(contentType)
            return response.newBuilder().body(body).build()
        }
        return response
    }
}