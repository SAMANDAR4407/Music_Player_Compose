package uz.gita.mymusicapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.mymusicapp.R
import uz.gita.mymusicapp.data.model.MusicData
import uz.gita.mymusicapp.ui.theme.MyMusicAppTheme
import uz.gita.mymusicapp.ui.theme.Purple80

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:39
 */

@Composable
fun MusicItem(
    musicData: MusicData,
    onClick:() -> Unit
) {
    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(70.dp)
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
        ) {
            Card(
                Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterVertically)
                    .size(56.dp),
                shape = RoundedCornerShape(7.dp),
                colors = CardDefaults.cardColors(Purple80),

            ) {
                Image(
                    painter = painterResource(id = R.drawable.music),
                    contentDescription = "",
                    Modifier.fillMaxSize().padding(5.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()) {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(0.dp)) {
                    Text(
                        text = musicData.title ?: stringResource(R.string.unknown_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        text = musicData.artist ?: stringResource(R.string.unknown_artist),
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.tertiary)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 10.dp),
                    colors = CardDefaults.cardColors(Color.Black)
                ){}
            }
        }
    }
}

@Preview
@Composable
fun MusicItemPreview() {
    MyMusicAppTheme() {

    }
}