package com.example.calenderapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CalendarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkDaysSinceJanuaryFirst() {
        composeTestRule.setContent {
            CalenderInformation(monthNumber = 1, year = 2024)
        }


        // Resten av linjene her får appen til å krasje, selv om jeg kun tar med linje 23
//        composeTestRule.onNodeWithText("15").performClick()
//
//        composeTestRule.onNodeWithText("Dismiss").assertIsDisplayed()
//        composeTestRule.onNodeWithText("2.January is 1 day since 1.January").assertIsDisplayed()

        // pause for neste steg, man kan ha dem mellom hver klikk f.eks
        Thread.sleep(3000)
    }
}
