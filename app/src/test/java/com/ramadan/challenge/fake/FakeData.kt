package com.ramadan.challenge.fake

import com.ramadan.challenge.domain.entity.Restaurant

object FakeData {
     fun givenData(): List<Restaurant> {
         val data = ArrayList<Restaurant>()
        val restaurant = Restaurant(
            id = "id 1",
            name = "ramadan",
            city = "mansoura",
            address = "Gamaa street",
            latitude = 31.34111,
            longitude = 31.1233
        )
         for (i in 1..10){
             data.add(restaurant)
         }

        return data
    }

//     fun givenRatesData(): Rates {
//        return Rates(kES = 107.0, tZS = 2319.0,nGN = 410.0,uGX = 3535.0)
//    }
}