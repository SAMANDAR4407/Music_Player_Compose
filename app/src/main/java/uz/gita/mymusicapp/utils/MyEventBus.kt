package uz.gita.mymusicapp.utils

import android.database.Cursor
import kotlinx.coroutines.flow.MutableStateFlow
import uz.gita.mymusicapp.data.model.CursorEnum
import uz.gita.mymusicapp.data.model.MusicData

/**
 *    Created by Kamolov Samandar on 06.06.2023 at 11:05
 */

object MyEventBus {
    var storageCursor: Cursor? = null
    var roomCursor: Cursor? = null

    var currentCursorEnum: CursorEnum? = null

    var storagePos: Int = -1
    var roomPos: Int = -1

    var totalTime: Int = 0
    var currentTime = MutableStateFlow(0)
    val currentTimeFlow = MutableStateFlow(0)

    var isPlaying = MutableStateFlow(false)
    val currentMusicData = MutableStateFlow<MusicData?>(null)

    var height = MutableStateFlow(0)
}