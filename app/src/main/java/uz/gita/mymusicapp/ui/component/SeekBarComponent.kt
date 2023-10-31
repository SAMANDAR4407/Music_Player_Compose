package uz.gita.mymusicapp.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.mymusicapp.ui.theme.MyMusicAppTheme

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:35
 */

@Composable
fun SeekbarComponent(
    currentTime: Int,
    totalTime: Int,
    changeListener: (Float) -> Unit
) {
    var seekBarPosition by remember { mutableStateOf(currentTime.toFloat()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight()
    ) {
        Slider(
            value = seekBarPosition,
            onValueChange = {
                seekBarPosition = it
//                    Log.d("TTT", it.toString())
            },
            valueRange = 0f..totalTime.toFloat(),
            onValueChangeFinished = {
                changeListener.invoke(seekBarPosition)
//                    Log.d("TTT", "finish")
            },
            steps = 1000,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF000000),
                activeTickColor = Color(0xFF000000),
                activeTrackColor = Color(0xFFCCC2C2)
            )
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = seekBarPosition.toString(),
                color = MaterialTheme.colorScheme.background
            )

            Text(
                text = totalTime.toString()
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SeekbarComponentPreview() {
    MyMusicAppTheme() {
//        SeekbarComponent(
//            currentTime = 60_000L,
//            totalTime = 180_000L,
//            changeListener = {
//                Log.d("TTT", "result $it")
//            }
//        )
    }
}