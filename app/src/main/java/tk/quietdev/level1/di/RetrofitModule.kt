package tk.quietdev.level1.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tk.quietdev.level1.network.RetrofitService
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://restcountries.eu/rest/v2/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()



    @Provides
    @Singleton
    fun provideRetrofitService(retrofit : Retrofit) : RetrofitService = retrofit.create(RetrofitService::class.java)


}