package uz.gita.mymusicapp.data.model

import uz.gita.mymusicapp.data.local.entity.MusicEntity

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:08
 */

data class MusicData(
    val id: Int,
    val artist: String?,
    val title: String?,
    val data: String?,
    val duration: Long,
) {
    fun toEntity() = MusicEntity(id, artist, title, data, duration)
}
