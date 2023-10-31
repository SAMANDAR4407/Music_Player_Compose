package uz.gita.mymusicapp.presentation.music

import android.content.Context
import org.orbitmvi.orbit.ContainerHost

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:44
 */

sealed interface MusicListContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        object Loading : UIState
        object PreparedData : UIState
    }

    sealed interface SideEffect {
        object OpenPermissionDialog : SideEffect
        object StartMusicService : SideEffect
    }

    sealed interface Intent {
        data class LoadMusics(val context: Context) : Intent
        object PlayMusic : Intent
        object OpenPlayScreen : Intent
        object OpenPlayListScreen : Intent
        object RequestPermission : Intent
    }

}