package org.closs.core.presentation.shared.ui.components.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.Navigator

@Composable
fun BackHandlerComponent(navigator: Navigator, callBack: (() -> Unit)? = null) {
    val scope = rememberCoroutineScope()
    BackHandler {
        if (callBack != null) {
            callBack()
        } else {
            scope.launch(Dispatchers.Main.immediate) {
                awaitFrame()
                navigator.navigateUp()
            }
        }
    }
}
