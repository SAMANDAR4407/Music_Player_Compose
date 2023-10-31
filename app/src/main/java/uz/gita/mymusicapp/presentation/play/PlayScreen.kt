package uz.gita.mymusicapp.presentation.play

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.mymusicapp.R
import uz.gita.mymusicapp.data.model.ActionEnum
import uz.gita.mymusicapp.data.model.CommandEnum
import uz.gita.mymusicapp.data.model.CursorEnum
import uz.gita.mymusicapp.data.model.MusicData
import uz.gita.mymusicapp.navigation.AppScreen
import uz.gita.mymusicapp.ui.theme.MyMusicAppTheme
import uz.gita.mymusicapp.ui.theme.Purple40
import uz.gita.mymusicapp.utils.MyEventBus
import uz.gita.mymusicapp.utils.base.getMusicDataByPosition
import uz.gita.mymusicapp.utils.base.getTime
import uz.gita.mymusicapp.utils.manageMusicService
import java.util.concurrent.TimeUnit

/**
 *    Created by Kamolov Samandar on 01.11.2023 at 0:07
 */

class PlayScreen : AppScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: PlayContract.ViewModel = getViewModel<PlayViewModel>()
        val uiState = viewModel.collectAsState()

        val musicData = MyEventBus.currentMusicData.collectAsState(
            initial = if (MyEventBus.currentCursorEnum == CursorEnum.SAVED)
                MyEventBus.roomCursor!!.getMusicDataByPosition(MyEventBus.roomPos)
            else MyEventBus.storageCursor!!.getMusicDataByPosition(MyEventBus.storagePos)
        )

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is PlayContract.SideEffect.UserAction -> {
                    when (sideEffect.actionEnum) {
                        ActionEnum.MANAGE -> {
                            manageMusicService(context, CommandEnum.MANAGE)
                        }

                        ActionEnum.NEXT -> {
                            manageMusicService(context, CommandEnum.NEXT)
                        }

                        ActionEnum.PREV -> {
                            manageMusicService(context, CommandEnum.PREV)
                        }

                        ActionEnum.UPDATE_SEEKBAR -> {
                            manageMusicService(context, CommandEnum.UPDATE_SEEKBAR)
                        }
                    }
                }
            }
        }

        MyMusicAppTheme() {
            Surface(color = MaterialTheme.colorScheme.background) {
                Scaffold(
                    topBar = { TopBar(musicData, uiState, viewModel::onEventDispatcher) }) {
                    PlayScreenContent(
                        musicData,
                        viewModel::onEventDispatcher,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    musicData: State<MusicData?>,
    uiState: State<PlayContract.UIState>,
    onEventDispatcher: (PlayContract.Intent) -> Unit
) {

    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow

    onEventDispatcher(PlayContract.Intent.CheckMusic(musicData.value!!))

    when (uiState.value) {
        is PlayContract.UIState.CheckMusic -> {
            val isSaved = (uiState.value as PlayContract.UIState.CheckMusic).isSaved
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple40)
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                IconButton(modifier = Modifier.padding(15.dp), onClick = { navigator.pop() }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, tint = Color.White, contentDescription = "")
                }
                IconButton(modifier = Modifier
                    .padding(10.dp)
                    .size(35.dp), onClick = {
                    if (isSaved) {
                        onEventDispatcher.invoke(
                            PlayContract.Intent.DeleteMusic(musicData.value!!)
                        )

                    } else {
                        onEventDispatcher.invoke(
                            PlayContract.Intent.SaveMusic(musicData.value!!)
                        )
                    }

                    onEventDispatcher.invoke(PlayContract.Intent.CheckMusic(musicData.value!!))
                }) {
                    Icon(
                        painterResource(id = R.drawable.like),
                        tint = if (isSaved) Color.Red else Color.White,
                        contentDescription = ""
                    )
                }
            }
        }

        else -> {}
    }
}

@Composable
fun PlayScreenContent(
    musicData: State<MusicData?>,
    eventListener: (PlayContract.Intent) -> Unit,
    modifier: Modifier
) {

    val seekBarState = MyEventBus.currentTimeFlow.collectAsState(initial = 0)
    var seekBarValue by remember { mutableStateOf(seekBarState.value) }
    val musicIsPlaying = MyEventBus.isPlaying.collectAsState()

    val milliseconds = musicData.value!!.duration
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    val minutes = (milliseconds / 1000 / 60) % 60
    val seconds = (milliseconds / 1000) % 60

    val duration = if (hours == 0L) "%02d:%02d".format(minutes, seconds) // 03:45
    else "%02d:%02d:%02d".format(hours, minutes, seconds) // 01:03:45

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Purple40)
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
                .align(CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0x80FFFFFF))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    modifier = Modifier.aspectRatio(
                        ratio = 1f,
                        matchHeightConstraintsFirst = true
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = musicData.value!!.artist ?: "Unknown",
                style = TextStyle(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = musicData.value!!.title ?: "Unknown",
                Modifier.padding(horizontal = 10.dp),
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Slider(
                value = seekBarState.value.toFloat(),
                onValueChange = { newState ->
                    seekBarValue = newState.toInt()
                    eventListener.invoke(PlayContract.Intent.UserAction(ActionEnum.UPDATE_SEEKBAR))
                },
                onValueChangeFinished = {
                    MyEventBus.currentTime.value = seekBarValue
                    eventListener.invoke(PlayContract.Intent.UserAction(ActionEnum.UPDATE_SEEKBAR))
                },
                valueRange = 0f..musicData.value!!.duration.toFloat(),
                steps = 1000,
                colors = if (!isSystemInDarkTheme()) SliderDefaults.colors(
                    thumbColor = Color(0xFFCCC2C2),
                    activeTickColor = Color(0xFF464646),
                    activeTrackColor = Color(0xFFCCC2C2)
                ) else SliderDefaults.colors(
                    thumbColor = Color(0xFFCCC2C2),
                    activeTickColor = Color(0xFF464646),
                    activeTrackColor = Color(0xFFCCC2C2)
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f),
                    text = getTime(seekBarState.value / 1000),
                    color = Color.White
                )
                // 03:45
                Text(
                    modifier = Modifier
                        .width(0.dp)
                        .weight(1f),
                    textAlign = TextAlign.End,
                    text = duration,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

                IconButton(
                    onClick = {
                        eventListener(PlayContract.Intent.UserAction(ActionEnum.PREV))
                        seekBarValue = 0
                    }, modifier = Modifier
                        .size(55.dp)
                        .padding(10.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.next_prev), tint = Color.White, contentDescription = "prev")
                }
                IconButton(
                    onClick = { eventListener(PlayContract.Intent.UserAction(ActionEnum.MANAGE)) }, modifier = Modifier
                        .size(80.dp)
                        .padding(10.dp)
                ) {
                    Icon(painter = painterResource(id = if (musicIsPlaying.value) R.drawable.pause else R.drawable.play), tint = Color.White, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        eventListener(PlayContract.Intent.UserAction(ActionEnum.NEXT))
                        seekBarValue = 0
                    }, modifier = Modifier
                        .size(55.dp)
                        .padding(10.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.next_prev), tint = Color.White, modifier = Modifier.rotate(180f), contentDescription = "next")
                }
            }
        }
    }
}