package com.example.calenderapp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun daysSinceJanuaryFirst_MarchFirstNotLeapYear() {
        val date = 1
        val month = 3
        val year = 2023
        val expectedNumberOfDays = "59"
        val actualNumberOfDays = daysSinceJanuaryFirst(1, 3, 2023)
        assertEquals(expectedNumberOfDays, actualNumberOfDays)
    }

    @Test
    fun daysSinceJanuaryFirst_MarchFirstLeapYear() {
        val date = 1
        val month = 3
        val year = 2024
        val expectedNumberOfDays = "60"
        val actualNumberOfDays = daysSinceJanuaryFirst(1, 3, 2024)
        assertEquals(expectedNumberOfDays, actualNumberOfDays)
    }

    @Test
    fun isLeapYear_twentyTwentyFour() {
        val year = 2024
        val expectedBool: Boolean = true
        val actualBool = isLeapYear(2024)
        assertEquals(expectedBool, actualBool)
    }

    @Test
    fun isLeapYear_nineteenHundred() {
        val year = 1900
        val expectedBool: Boolean = false
        val actualBool = isLeapYear(1900)
        assertEquals(expectedBool, actualBool)
    }
    @Test
    fun isLeapYear_twoThousand() {
        val year = 2000
        val expectedBool: Boolean = true
        val actualBool = isLeapYear(2000)
        assertEquals(expectedBool, actualBool)
    }
}

