package uz.gita.mymusicapp.domain

import android.database.Cursor
import kotlinx.coroutines.flow.Flow
import uz.gita.mymusicapp.data.model.MusicData

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 22:48
 */

interface AppRepository {
    fun addMusic(musicData: MusicData)
    fun deleteMusic(musicData: MusicData)
    fun getAllMusics(): Flow<List<MusicData>>
    fun getSavedMusics(): Cursor
    fun checkMusicIsSaved(musicData: MusicData): Boolean
}