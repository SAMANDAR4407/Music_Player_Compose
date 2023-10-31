package uz.gita.mymusicapp.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import uz.gita.mymusicapp.data.model.CommandEnum
import uz.gita.mymusicapp.service.MusicService

fun manageMusicService(context: Context, commandEnum: CommandEnum) {
    val intent = Intent(context, MusicService::class.java)
    intent.putExtra("COMMAND", commandEnum)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else context.startService(intent)
}