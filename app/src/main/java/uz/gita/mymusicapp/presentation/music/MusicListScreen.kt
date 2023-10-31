package uz.gita.mymusicapp.presentation.music

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.*
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.mymusicapp.MainActivity
import uz.gita.mymusicapp.R
import uz.gita.mymusicapp.data.model.CommandEnum
import uz.gita.mymusicapp.data.model.CursorEnum
import uz.gita.mymusicapp.navigation.AppScreen
import uz.gita.mymusicapp.presentation.play.PlayScreen
import uz.gita.mymusicapp.ui.component.LoadingComponent
import uz.gita.mymusicapp.ui.component.MusicItem
import uz.gita.mymusicapp.ui.theme.MyMusicAppTheme
import uz.gita.mymusicapp.utils.MyEventBus
import uz.gita.mymusicapp.utils.base.checkPermissions
import uz.gita.mymusicapp.utils.base.getMusicDataByPosition
import uz.gita.mymusicapp.utils.manageMusicService

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:47
 */

class MusicListScreen : AppScreen() {
    @Composable
    override fun Content() {
        val activity = LocalContext.current as MainActivity
        val viewModel: MusicListContract.ViewModel = getViewModel<MusicListViewModel>()
        val uiState = viewModel.collectAsState()

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                MusicListContract.SideEffect.StartMusicService -> {
                    MyEventBus.currentCursorEnum = CursorEnum.STORAGE
                    manageMusicService(activity, CommandEnum.PLAY)
                }

                MusicListContract.SideEffect.OpenPermissionDialog -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        activity.checkPermissions(
                            arrayListOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.POST_NOTIFICATIONS,
                                Manifest.permission.READ_MEDIA_AUDIO,
                                Manifest.permission.READ_PHONE_STATE
                            )
                        ) {
                            viewModel.onEventDispatcher(MusicListContract.Intent.LoadMusics(activity))
                        }
                    } else {
                        activity.checkPermissions(
                            arrayListOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE
                            )
                        ) {
                            viewModel.onEventDispatcher(MusicListContract.Intent.LoadMusics(activity))
                        }
                    }
                }
            }
        }

        LifecycleEffect(
            onStarted = { viewModel.onEventDispatcher(MusicListContract.Intent.LoadMusics(activity)) }
        )

        MyMusicAppTheme {
            MusicListContent(uiState = uiState, eventListener = viewModel::onEventDispatcher)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicListContent(
    uiState: State<MusicListContract.UIState>,
    eventListener: (MusicListContract.Intent) -> Unit
) {

    val context = LocalContext.current
    val isPlaying = MyEventBus.isPlaying.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob ())

    val height = MyEventBus.height.collectAsState()

    when (uiState.value) {
        MusicListContract.UIState.Loading -> {
            LoadingComponent()
            eventListener.invoke(MusicListContract.Intent.RequestPermission)
        }

        MusicListContract.UIState.PreparedData -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                fontWeight = FontWeight.Medium,
                                fontSize = 22.sp,
                                text = "Music Player"
                            )
                        },
                        actions = {
                            IconButton(modifier = Modifier
                                .padding(10.dp)
                                .size(35.dp),
                                onClick = { eventListener(MusicListContract.Intent.OpenPlayListScreen) }) {
                                Icon(painterResource(id = R.drawable.like), tint = Color.Red, contentDescription = "")
                            }
                        }
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    for (pos in 0 until MyEventBus.storageCursor!!.count) {
                        item {
                            MusicItem(musicData = MyEventBus.storageCursor!!.getMusicDataByPosition(pos)) {
                                MyEventBus.storagePos = pos
                                eventListener.invoke(MusicListContract.Intent.PlayMusic)
                                eventListener.invoke(MusicListContract.Intent.OpenPlayScreen)
                                //                                if (MyEventBus.storagePos != pos) {
                                //                                    MyEventBus.storagePos = pos
                                //                                    scope.launch { MyEventBus.height.emit(90) }
                                //                                    eventListener.invoke(MusicListContract.Intent.PlayMusic)
                                //                                    eventListener.invoke(MusicListContract.Intent.OpenPlayScreen)
                                //                                } else {
                                //                                    scope.launch { MyEventBus.height.emit(90) }
                                //                                    eventListener.invoke(MusicListContract.Intent.PlayMusic)
                                //                                    eventListener.invoke(MusicListContract.Intent.OpenPlayScreen)
                                //                                }
                            }
                        }
                    }
                }
            }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = BottomCenter) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.value.dp)
                .background(Color.Transparent)
                .padding(8.dp)
                .clickable {
                    navigator.push(PlayScreen())
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(colorResource(id = R.color.purple_200))
        ) {
            val musicData = MyEventBus.currentMusicData.collectAsState()
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = musicData.value?.title ?: "Unknown",
                    Modifier
                        .width(0.dp)
                        .weight(1f)
                        .align(CenterVertically),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(10.dp))

                IconButton(onClick = {
                    if (MyEventBus.currentMusicData.value != null) {
                        MyEventBus.currentCursorEnum = CursorEnum.STORAGE
                        manageMusicService(context, CommandEnum.MANAGE)
                    }
                }) {
                    Icon(painter = painterResource(id = if (isPlaying.value) R.drawable.pause else R.drawable.play), tint = Color.White, modifier = Modifier.fillMaxSize(), contentDescription = "")
                }

            }
        }
    }

}