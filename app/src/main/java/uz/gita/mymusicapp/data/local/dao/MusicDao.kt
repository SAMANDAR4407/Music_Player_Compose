package uz.gita.mymusicapp.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.mymusicapp.data.local.entity.MusicEntity

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:38
 */

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMusic(musicEntity: MusicEntity)

    @Query("select * from musics where data = :data")
    fun checkMusicSaved(data: String): MusicEntity?

    @Delete
    fun deleteMusic(musicEntity: MusicEntity)

    @Query("select * from musics")
    fun retrieveAllMusics() : Flow<List<MusicEntity>>

    @Query("select * from musics")
    fun getSavedMusics() : Cursor
}