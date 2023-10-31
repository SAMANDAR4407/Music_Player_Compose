package uz.gita.mymusicapp.domain

import android.database.Cursor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.gita.mymusicapp.data.local.dao.MusicDao
import uz.gita.mymusicapp.data.model.MusicData
import javax.inject.Inject

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 22:48
 */

class AppRepositoryImpl @Inject constructor(
    private val dao: MusicDao
) : AppRepository {
    override fun addMusic(musicData: MusicData) {
        dao.addMusic(musicData.toEntity())
    }

    override fun deleteMusic(musicData: MusicData) {
        dao.deleteMusic(musicData.toEntity())
    }

    override fun getAllMusics(): Flow<List<MusicData>> =
        dao.retrieveAllMusics().map { list ->
            list.map { musicEntity ->
                musicEntity.toData()
            }
        }


    override fun getSavedMusics(): Cursor = dao.getSavedMusics()

    override fun checkMusicIsSaved(musicData: MusicData): Boolean {
        val data = dao.checkMusicSaved(musicData.data ?: "")
        return data != null
    }
}