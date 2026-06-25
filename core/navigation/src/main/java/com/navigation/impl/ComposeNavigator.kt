package com.navigation.impl

import com.navigation.api.NavigationCommand
import com.navigation.api.Navigator
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ComposeNavigator: Navigator {
    private val _navigationCommands = MutableSharedFlow<NavigationCommand>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val navigationCommands: SharedFlow<NavigationCommand> = _navigationCommands.asSharedFlow()

    override fun navigateTo(route: Any) {
        _navigationCommands.tryEmit(NavigationCommand.To(route))
    }

    override fun navigateUp() {
        _navigationCommands.tryEmit(NavigationCommand.Back)
    }
}