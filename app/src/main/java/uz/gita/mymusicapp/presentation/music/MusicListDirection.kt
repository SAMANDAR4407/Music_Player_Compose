package uz.gita.mymusicapp.presentation.music

import uz.gita.mymusicapp.navigation.AppNavigator
import uz.gita.mymusicapp.presentation.play.PlayScreen
import uz.gita.mymusicapp.presentation.playlist.PlayListScreen
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:44
 */

interface MusicListDirection {
    suspend fun navigateToPlayScreen()
    suspend fun navigateToPlayListScreen()
}


class MusicListDirectionImpl @Inject constructor(
    private val navigator: AppNavigator
) : MusicListDirection {

    override suspend fun navigateToPlayScreen() {
        navigator.navigateTo(PlayScreen())
    }

    override suspend fun navigateToPlayListScreen() {
        navigator.navigateTo(PlayListScreen())
    }
}