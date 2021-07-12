package com.ramadan.challenge

import com.ramadan.challenge.domain.inteactor.GetRestaurantsTest
import com.ramadan.challenge.feature.map.MapRestaurantsViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    GetRestaurantsTest::class,
    MapRestaurantsViewModelTest::class
)
class AppTestSuite
