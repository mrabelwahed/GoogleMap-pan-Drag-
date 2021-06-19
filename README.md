
## Challenge


You work for an imaginary money transmitter, PenguinPay. Penguin pay sends money from the US (the sending market) to several countries in Africa (the receiving markets), where it pays out to different delivery networks such as M-Pesa in Kenya and MTN in Uganda. As part of PenguinPay’s growth strategy they are looking to launch a new sending market, the (fictitious) country of Binaria.
Binaria is home to multiple diaspora who are eager to send money to their friends and families back in their home countries. Binaria has one main requirement for remitting money, one which PenguinPay will need to take into account in their app: residents of Binaria are only allowed to use binary numbers.
For example, where in the US a user would enter “22” if they wished to send $22, in Binaria they must enter “010110”. If a resident of Binaria wishes to see how much money in local currency the recipient will receive, it too must be displayed in binary. For example, entering ‘010110’ for a recipient in Nigeria should show ‘01111100010001’ ($22 converted to NGN at current exchange rate of 1 USD = 361.50 NGN). Binaria has pegged its currency to the US Dollar.
Your job is to design a prototype of the ‘Send Transactions’ screen for PenguinPay’s upcoming launch. The PenguinPay Product Manager has given you the following requirement:
● Allow the user to enter the first and last name of a recipient
● Allow the user to enter the phone number of the recipient
● Either the user should be able to select the recipient's country and the phone number
should be validated based on it OR the country should be determined based on the phone number entered. Only phone numbers Kenya, Nigeria, Tanzania, or Uganda should be allowed.
● Allow the user to enter the amount they wish to send in binary numbers
● Display to the user the amount the recipient will receive in binary numbers with the
appropriate local currency indicated.
● Allow the user to press send, at which point an appropriate message should be given to
the user that their transaction is being sent.
● The amount the recipient will receive should be converted from Binarian Dollars to local
currency at current market rates. As Binarian Dollars are pegged to US Dollars, you can use US Dollar (USD) exchange rates.
● Current exchange rates can be accessed via APIs from openexchangerates.org (free plan) using the latest.json endpoint (the other endpoints are not available on the free plan)

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





