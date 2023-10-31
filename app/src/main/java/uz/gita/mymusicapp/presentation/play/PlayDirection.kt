package uz.gita.mymusicapp.presentation.play

import uz.gita.mymusicapp.navigation.AppNavigator
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 01.11.2023 at 0:06
 */

interface PlayDirection {
    suspend fun pop()
}

class PlayDirectionImpl @Inject constructor(
    private val navigator: AppNavigator
) : PlayDirection {
    override suspend fun pop() {
        navigator.pop()
    }
}