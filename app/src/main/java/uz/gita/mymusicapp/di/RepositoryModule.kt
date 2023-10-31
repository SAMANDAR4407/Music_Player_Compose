package uz.gita.mymusicapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mymusicapp.domain.AppRepository
import uz.gita.mymusicapp.domain.AppRepositoryImpl
import javax.inject.Singleton

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 22:55
 */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository
}