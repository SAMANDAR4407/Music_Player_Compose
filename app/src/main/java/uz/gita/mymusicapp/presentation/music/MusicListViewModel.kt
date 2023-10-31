package uz.gita.mymusicapp.presentation.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.mymusicapp.utils.MyEventBus
import uz.gita.mymusicapp.utils.base.getMusicsCursor
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:46
 */

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val direction: MusicListDirection
) : ViewModel(), MusicListContract.ViewModel {

    override val container = container<MusicListContract.UIState, MusicListContract.SideEffect>(MusicListContract.UIState.Loading)

    override fun onEventDispatcher(intent: MusicListContract.Intent) {

        when (intent) {

            is MusicListContract.Intent.LoadMusics -> {
                intent.context.getMusicsCursor()
                    .onEach {
                        MyEventBus.storageCursor = it
                    }.launchIn(viewModelScope)
                intent { reduce { MusicListContract.UIState.PreparedData } }
            }

            is MusicListContract.Intent.PlayMusic -> {
                intent { postSideEffect(MusicListContract.SideEffect.StartMusicService) }
            }

            is MusicListContract.Intent.RequestPermission -> {
                intent { postSideEffect(MusicListContract.SideEffect.OpenPermissionDialog) }
            }

            MusicListContract.Intent.OpenPlayListScreen -> {
                viewModelScope.launch { direction.navigateToPlayListScreen() }
            }

            MusicListContract.Intent.OpenPlayScreen -> {
                viewModelScope.launch { direction.navigateToPlayScreen() }
            }
        }
    }
}