package com.ramadan.challenge.core.navigator

import com.ramadan.challenge.domain.entity.Restaurant

/**
 * available screens
 */
enum class Screens {
    MAP,
    RESTAURANT_DETAILS
}

interface AppNavigator {
    fun navigateTo(screen: Screens, restaurant: Restaurant? = null)
}
