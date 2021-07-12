package com.ramadan.test_utils

import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

private fun <T> anyObject(): T {
    return Mockito.anyObject<T>()
}

private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
