package com.ramadan.challenge.ui

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TransactionActivityTest{
   @get:Rule
   var hiltRule = HiltAndroidRule(this)
//
    @Before
    fun setup(){
       hiltRule.inject()
    }

    @Test
    fun testOOOO(){

    }
}