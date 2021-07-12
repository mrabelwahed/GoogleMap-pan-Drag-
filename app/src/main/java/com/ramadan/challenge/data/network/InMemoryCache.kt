package com.ramadan.challenge.data.network

import com.ramadan.challenge.domain.entity.Restaurant

object InMemoryCache {
    private val cache = ArrayList<Restaurant>()

    fun add(restaurants: List<Restaurant>) {
        cache.addAll(restaurants)
    }

    fun get(): List<Restaurant> {
        return cache
    }
}
