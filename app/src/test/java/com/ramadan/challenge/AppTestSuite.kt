package com.ramadan.challenge

import com.ramadan.challenge.domain.inteactor.GetExchangeRatesTest
import com.ramadan.challenge.feature.transaction.TransactionViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    GetExchangeRatesTest::class,
    TransactionViewModelTest::class
)
class AppTestSuite
