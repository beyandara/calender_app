package com.example.calenderapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
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
            CalendarLayout(monthNumber = 4, year = 2024)
        }

        Thread.sleep(2000)

        composeTestRule.onNode(
            hasText("15")
            and
            hasClickAction()
        ).performClick()
        Thread.sleep(3000)

        composeTestRule.onNodeWithText("Dismiss").assertIsDisplayed()

        composeTestRule.onNodeWithText("15.April is 105 days since 1.January").assertIsDisplayed()
        Thread.sleep(1000)

    }
}


