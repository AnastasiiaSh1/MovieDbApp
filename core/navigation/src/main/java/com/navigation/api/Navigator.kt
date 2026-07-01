package com.navigation.api

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object Details

interface Navigator {
    fun navigateTo(destination: Any)
    fun navigateBack()
}