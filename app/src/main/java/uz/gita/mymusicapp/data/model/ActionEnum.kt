package uz.gita.mymusicapp.data.model

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 13:51
 */

enum class ActionEnum(val amount: Int) {
    MANAGE(6), PREV(1),
    NEXT(2), UPDATE_SEEKBAR(3)
}