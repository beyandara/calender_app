package com.example.calenderapp

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calenderapp.ui.theme.CalenderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalenderAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendarLayout()
                }
            }
        }
    }
}

@Composable
fun CalendarLayout(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(
            modifier = Modifier
                .height(40.dp)
        )
        Row (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ){
            Text(
                text = showMonth(monthNumber = 2),
                fontWeight = FontWeight.Bold,
                color = Color.Green,
            )
        }
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)

            )
            Row (
                modifier = Modifier
                    .size(width = 400.dp, height = 36.dp)
                    .border(BorderStroke(2.dp, Color.Black))
            ){
                Text(
                    text = "Row test",
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row (
            modifier = Modifier
                .border(BorderStroke(2.dp, Color.Black))
        ){
            Text(
                text = stringResource(R.string.bottom_click_text)
            )
        }
    }
}

@Composable
fun showMonth(monthNumber: Int): String {
    val numberToMonthName = when (monthNumber) {
        1 -> stringResource(R.string.month_1)
        2 -> stringResource(R.string.month_2)
        3 -> stringResource(R.string.month_3)
        4 -> stringResource(R.string.month_4)
        5 -> stringResource(R.string.month_5)
        6 -> stringResource(R.string.month_6)
        7 -> stringResource(R.string.month_7)
        8 -> stringResource(R.string.month_8)
        9 -> stringResource(R.string.month_9)
        10 -> stringResource(R.string.month_10)
        11 -> stringResource(R.string.month_11)
        else -> stringResource(R.string.month_12)
    }
    return numberToMonthName
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalenderAppTheme {
        CalendarLayout()
    }
}