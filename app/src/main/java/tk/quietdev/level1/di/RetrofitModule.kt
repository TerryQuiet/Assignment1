package tk.quietdev.level1.di


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tk.quietdev.level1.BuildConfig
import tk.quietdev.level1.data.remote.UserApi
import tk.quietdev.level1.utils.httpIntercepors.MyInterceptor
import tk.quietdev.level1.utils.httpIntercepors.TokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideTokenInterceptor() = TokenInterceptor()

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(MyInterceptor())
            .build()

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, mosh: Moshi): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(mosh))
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun userApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

}