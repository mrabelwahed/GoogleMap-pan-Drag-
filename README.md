
## Challenge

1. Display restaurants around the user’s current location on a map ○
 Use the FourSquare Search API to query for restaurants: https://developer.foursquare.com/docs/api/venues/search
2. Load more restaurants when the user pans the map.
 2.1 Cache results in-memory (no need to persist the cache).
 2.2 Read restaurants from the cache to show results early, but only if the restaurants
fit within the user’s current viewport.
3. Include a simple restaurant detail page.

## before run

1. Get Client ID and Secret fro FourSquare and update AppConst file
2- Get your Google Map key and Update google_maps.xml file

## How to run the project

 1. Download the project
 2. Import the project to Android Studio Tool
 3. Run the project
 4. Check the Emulator or your real device

 ## How to run the unit and instrumentation tests

 1. Make sure your emulator is up running (for instrumentation test only)
 2. OPen the terminal from Android Studio
 3- type the following command in terminal

    ./gradlew test connectedAndroidTest

 ## Run Ktlint

 1. to check the formatting. Run the following command in terminal
     ./gradlew ktlintCheck
 2. to format the code. Run the following command in terminal
    ./gradlew ktlintFormat

 ## Run Lint
 1. open terminal and run ./gradlew lint
 2. check issues like accessibility and find lint-results file under build/reports


## The App Architecture

 1. Clean Architecture based on  the MVVM Architecture pattern with Interactors

## Libs

1. Hilt for dependency Injection
2. RxJava for Handling Threading ,  powerful operators and Reactive Programming
3. Architecture Components like ViewModel and LiveData
4. Junit for Assertions
5. Mockito for mocking objects





