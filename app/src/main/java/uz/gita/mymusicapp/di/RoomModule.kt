package uz.gita.mymusicapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.mymusicapp.data.local.dao.MusicDao
import uz.gita.mymusicapp.data.local.db.AppDatabase
import javax.inject.Singleton

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 22:51
 */

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @[Provides Singleton]
    fun provideDB(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "musics"
    ).allowMainThreadQueries().build()

    @[Provides Singleton]
    fun provideMusicDao(db: AppDatabase): MusicDao = db.getMusicDao()
}