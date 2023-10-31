package uz.gita.mymusicapp.presentation.playlist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.*
import uz.gita.mymusicapp.MainActivity
import uz.gita.mymusicapp.R
import uz.gita.mymusicapp.data.model.CommandEnum
import uz.gita.mymusicapp.data.model.CursorEnum
import uz.gita.mymusicapp.navigation.AppScreen
import uz.gita.mymusicapp.ui.component.LoadingComponent
import uz.gita.mymusicapp.ui.component.MusicItem
import uz.gita.mymusicapp.ui.theme.MyMusicAppTheme
import uz.gita.mymusicapp.utils.MyEventBus
import uz.gita.mymusicapp.utils.base.getMusicDataByPosition
import uz.gita.mymusicapp.utils.manageMusicService

/**
 *    Created by Kamolov Samandar on 01.11.2023 at 0:17
 */

class PlayListScreen : AppScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val activity = LocalContext.current as MainActivity
        val viewModel: PlayListContract.ViewModel = getViewModel<PlayListViewModel>()
        val uiState = viewModel.collectAsState()

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                PlayListContract.SideEffect.StartMusicService -> {
                    MyEventBus.currentCursorEnum = CursorEnum.SAVED
                    manageMusicService(activity, CommandEnum.PLAY)
                }
            }
        }

        LifecycleEffect(
            onStarted = { viewModel.onEventDispatcher(PlayListContract.Intent.CheckMusicExistence) }
        )

        val navigator = LocalNavigator.currentOrThrow

        MyMusicAppTheme() {
            Surface(modifier = Modifier.fillMaxSize()) {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text(text = "Favorites", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(modifier = Modifier.padding(10.dp), onClick = {
                                navigator.pop()
                            }) {
                                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "")
                            }
                        }
                    )
                }) {
                    PlayListScreenContent(
                        uiState = uiState,
                        eventListener = viewModel::onEventDispatcher,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}

@Composable
fun PlayListScreenContent(
    uiState: State<PlayListContract.UIState>,
    eventListener: (PlayListContract.Intent) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState.value) {

            PlayListContract.UIState.IsExistMusic -> {
                LoadingComponent()
                eventListener.invoke(PlayListContract.Intent.LoadMusics)
            }

            PlayListContract.UIState.Loading -> {
                eventListener.invoke(PlayListContract.Intent.CheckMusicExistence)
            }

            is PlayListContract.UIState.PreparedData -> {
                if (MyEventBus.roomCursor!!.count == 0) {
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            modifier = Modifier
                                .size(180.dp),
                            painter = painterResource(id = R.drawable.music_list_placeholder),
                            contentDescription = null
                        )
                        Text(text = "No music", fontWeight = FontWeight.Medium, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
                    }
                } else {
                    LazyColumn {
                        for (pos in 0 until MyEventBus.roomCursor!!.count) {
                            item {
                                MusicItem(
                                    musicData = MyEventBus.roomCursor!!.getMusicDataByPosition(pos),
                                    onClick = {
                                        MyEventBus.roomPos = pos
                                        eventListener.invoke(PlayListContract.Intent.PlayMusic)
                                        eventListener.invoke(PlayListContract.Intent.OpenPlayScreen)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}