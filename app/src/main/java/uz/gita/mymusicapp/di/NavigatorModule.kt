package uz.gita.mymusicapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mymusicapp.navigation.AppNavigator
import uz.gita.mymusicapp.navigation.NavigationDispatcher
import uz.gita.mymusicapp.navigation.NavigationHandler
import javax.inject.Singleton

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 22:57
 */

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @[Binds Singleton]
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

    @[Binds Singleton]
    fun bindNavigatorHandler(impl: NavigationDispatcher): NavigationHandler

}