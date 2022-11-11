package nl.donyell.rabobank.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.donyell.data.FileManager
import nl.donyell.data.IssueRepositoryImpl
import nl.donyell.domain.IssueRepository
import nl.donyell.rabobank.AndroidFileManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext applicationContext: Context): Context {
        return applicationContext
    }

    @Provides
    @Singleton
    fun providesCsvManager(androidCsvManager: AndroidFileManager): FileManager {
        return androidCsvManager
    }

    @Provides
    @Singleton
    fun provideCsvRepository(
        csvRepositoryImpl: IssueRepositoryImpl
    ): IssueRepository {
        return csvRepositoryImpl
    }
}
