package uz.gita.mymusicapp.presentation.playlist

import org.orbitmvi.orbit.ContainerHost
import uz.gita.mymusicapp.data.model.MusicData

/**
 *    Created by Kamolov Samandar on 01.11.2023 at 0:15
 */

interface PlayListContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        object Loading : UIState
        object IsExistMusic : UIState
        object PreparedData : UIState
    }

    sealed interface SideEffect {
        object StartMusicService : SideEffect
    }

    sealed interface Intent {
        object CheckMusicExistence : Intent
        object LoadMusics : Intent
        object PlayMusic : Intent
        object OpenPlayScreen : Intent
        data class DeleteMusic(val musicData: MusicData) : Intent
    }

}