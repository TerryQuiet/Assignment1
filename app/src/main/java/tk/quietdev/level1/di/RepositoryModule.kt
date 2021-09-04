package tk.quietdev.level1.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tk.quietdev.level1.repository.LocalRepositoryImp
import tk.quietdev.level1.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Singleton
  @Provides
  fun provideLocalRepository(
    @ApplicationContext context: Context
  ) : Repository = LocalRepositoryImp(context)

}