package uz.gita.mymusicapp.presentation.playlist

import uz.gita.mymusicapp.navigation.AppNavigator
import uz.gita.mymusicapp.presentation.play.PlayScreen
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 01.11.2023 at 0:16
 */

interface Direction {
    suspend fun navigateToPlayScreen()
}

class PlayListDirection @Inject constructor(
    private val appNavigator: AppNavigator
) : Direction {

    override suspend fun navigateToPlayScreen() {
        appNavigator.navigateTo(PlayScreen())
    }
}