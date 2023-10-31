package uz.gita.mymusicapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.mymusicapp.data.model.MusicData

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:31
 */

@Entity(tableName = "musics")
data class MusicEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val artist: String?,
    val title: String?,
    val data: String?,
    val duration: Long,
) {
    fun toData() = MusicData(id, artist, title, data, duration)
}
