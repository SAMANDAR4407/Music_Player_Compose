package uz.gita.mymusicapp.navigation

import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.flow.SharedFlow

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 23:06
 */

typealias NavigationArgs = Navigator.() -> Unit

interface NavigationHandler {
    val navState: SharedFlow<NavigationArgs>
}