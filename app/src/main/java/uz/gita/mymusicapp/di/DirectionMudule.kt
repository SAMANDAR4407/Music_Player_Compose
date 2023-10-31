package uz.gita.mymusicapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.mymusicapp.presentation.music.MusicListDirection
import uz.gita.mymusicapp.presentation.music.MusicListDirectionImpl
import uz.gita.mymusicapp.presentation.play.PlayDirection
import uz.gita.mymusicapp.presentation.play.PlayDirectionImpl
import uz.gita.mymusicapp.presentation.playlist.Direction
import uz.gita.mymusicapp.presentation.playlist.PlayListDirection

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 22:58
 */

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @Binds
    fun bindMusicListDirection(impl: MusicListDirectionImpl): MusicListDirection

    @Binds
    fun bindPlayDirection(impl: PlayDirectionImpl): PlayDirection

    @Binds
    fun bindPlayListDirection(impl: PlayListDirection): Direction
}