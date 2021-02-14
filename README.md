
## Challenge

Your task is to build a simple weather app that can take a location input,
 then display some details about the weather. You should write your app with Kotlin or Java,
  use any libraries/frameworks you wish, and any weather API that is available.

## Assumption
 for Forcast data for next 14 days , I can't find free service to do this.
 SO I will assume I have 14 days forecast data in json file on the assets and I will
 work based on this assumption



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





