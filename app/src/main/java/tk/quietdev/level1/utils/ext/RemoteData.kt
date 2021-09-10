package tk.quietdev.level1.utils.ext

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tk.quietdev.level1.data.remote.models.RemoteData

fun RemoteData.jetJson(): String? {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(RemoteData::class.java).toJson(this)
}