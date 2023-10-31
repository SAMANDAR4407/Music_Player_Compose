package uz.gita.mymusicapp.data.model

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:51
 */

enum class CommandEnum(val amount: Int) {
    MANAGE(6), PREV(1),
    NEXT(2), PLAY(3),
    PAUSE(4), CLOSE(5),
    CONTINUE(7), UPDATE_SEEKBAR(8)
}