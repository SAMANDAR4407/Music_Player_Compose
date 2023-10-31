package uz.gita.mymusicapp.navigation

import cafe.adriel.voyager.androidx.AndroidScreen

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:04
 */

typealias AppScreen = AndroidScreen

interface AppNavigator {

    suspend fun navigateTo(screen: AppScreen)
    suspend fun pop()
    suspend fun replace(screen: AppScreen)
}