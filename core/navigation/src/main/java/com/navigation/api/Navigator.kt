package com.navigation.api

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object Details

interface Navigator {
    fun navigateToHome()
    fun navigateToDetails()
    fun navigateBack()
}