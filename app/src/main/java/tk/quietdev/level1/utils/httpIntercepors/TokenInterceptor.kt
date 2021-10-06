package tk.quietdev.level1.utils.httpIntercepors

import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor : Interceptor {

    var token: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (token.isNotEmpty()) {
            val finalToken = "Bearer " + token
            request = request.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        }
        return chain.proceed(request)
    }
}