package uz.gita.mymusicapp.presentation.play

import org.orbitmvi.orbit.ContainerHost
import uz.gita.mymusicapp.data.model.ActionEnum
import uz.gita.mymusicapp.data.model.MusicData

/**
 *    Created by Kamolov Samandar on 01.11.2023 at 0:05
 */

sealed interface PlayContract {

    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        object InitState : UIState
        data class CheckMusic(val isSaved: Boolean) : UIState
    }

    sealed interface SideEffect {
        data class UserAction(val actionEnum: ActionEnum) : SideEffect
    }

    sealed interface Intent {
        data class UserAction(val actionEnum: ActionEnum) : Intent
        data class SaveMusic(val musicData: MusicData) : Intent
        data class DeleteMusic(val musicData: MusicData) : Intent
        data class CheckMusic(val musicData: MusicData) : Intent
        object Pop : Intent
    }

}