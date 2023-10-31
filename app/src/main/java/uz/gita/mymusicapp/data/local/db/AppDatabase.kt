package uz.gita.mymusicapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.mymusicapp.data.local.dao.MusicDao
import uz.gita.mymusicapp.data.local.entity.MusicEntity

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:36
 */

@Database(entities = [MusicEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMusicDao() : MusicDao
}