package com.ramadan.challenge.core.navigator

import androidx.fragment.app.FragmentActivity
import com.ramadan.challenge.R
import com.ramadan.challenge.domain.entity.Restaurant
import com.ramadan.challenge.feature.restaurants.map.MapRestaurantsFragment
import com.ramadan.challenge.feature.restaurants.restaurant.RestaurantDetails
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screens, restaurant: Restaurant?) {
        val fragment = when (screen) {
            Screens.MAP -> MapRestaurantsFragment()
            Screens.RESTAURANT_DETAILS -> RestaurantDetails.newInstance(restaurant)
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.homeContainer, fragment)
            .addToBackStack(fragment.javaClass.canonicalName)
            .commit()
    }
}
