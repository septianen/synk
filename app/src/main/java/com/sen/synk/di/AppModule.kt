package com.sen.synk.di

import android.content.Context
import androidx.room.Room
import com.sen.synk.data.RetrofitInstance
import com.sen.synk.data.api.ApiService
import com.sen.synk.data.db.AppDatabase
import com.sen.synk.data.constant.DB
import com.sen.synk.data.dao.AccountDao
import com.sen.synk.data.repository.AlbumRepository
import com.sen.synk.domain.usecase.AccountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao {
        return appDatabase.accountDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(accountDao: AccountDao): AccountUseCase {
        return AccountUseCase(accountDao)
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(@ApplicationContext appContext: Context): AlbumRepository {
        return AlbumRepository(
            RetrofitInstance.buildRetrofit(context = appContext).create(ApiService::class.java)
        )
    }
}