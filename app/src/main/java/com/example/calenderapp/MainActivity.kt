package com.example.calenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
                    CalenderInformation()
                }
            }
        }
    }
}

@Composable
fun CalenderInformation(monthNumber: Int = 1) {
    var clicked by remember { mutableStateOf(false) } // Flyttet clicked hit


    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(
            modifier = Modifier
                .height(40.dp)
        )
        // month display
        Row (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp)
                .border(BorderStroke(0.dp, Color.Black))
                .height(46.dp)
        ){
            Text(
                text = showMonth(monthNumber = monthNumber),
                fontWeight = FontWeight.Bold,
                color = Color.Green,
            )
        }

        // function to initialize calender - fra chatgpt
        CalendarLayout(onCardClick = { clicked = true })
        if (clicked) {
            MinimalDialog(onDismissRequest = { clicked = false})
        }


        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Black))
                .height(46.dp)
        ){
            Text(
                text = stringResource(R.string.bottom_click_text),

            )
        }
    }
}
@Composable
fun WeekDays() {
    val listOfDays = listOf("", "mon", "tue", "wed", "thu", "fri", "sat", "sun")
    LazyRow(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .border(BorderStroke(0.dp, Color.Black))
            .height(46.dp)
            .width(46.dp)


    ) {
        items(count = listOfDays.size) { index ->
            Box(
                modifier = Modifier
                    .border(0.dp, Color.Black)
                    .height(46.dp)
                    .width(48.dp)

            ) {
                Text(text = listOfDays[index],
                    modifier = Modifier
                        .align(Alignment.Center))
            }
        }
    }
}

@Composable
fun WeekNumbers() {
    val listOfWeeks = listOf("1", "2", "3", "4", "5") // Dette skal bli parameterisert
    LazyColumn(
        contentPadding = PaddingValues(start = 5.dp),
    ) {
        items(count = listOfWeeks.size) { index ->
            Box(
                modifier = Modifier
                    .border(0.dp, Color.Black)
                    .height(46.dp)
                    .width(48.dp)
            ) {
                Text(
                    text = listOfWeeks[index],
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CalendarLayout(onCardClick: () -> Unit) {
//    var clicked by remember { mutableStateOf(false) }
    Box {
        Column {
            WeekDays()
            Row {
                WeekNumbers()
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = GridCells.Fixed(count = 7),
                    contentPadding = PaddingValues(end = 5.dp)
                ) {

                    itemsIndexed(
                        items =  listOfDaysInMonth(2024, 2)
                    ) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 0.dp, color = Color.Blue)
                                .height(height = 46.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onCardClick() }
                            ) {

                                Text(
                                    text = item.toString(), // Viser tallene i lista øverst
                                    modifier = Modifier
                                        .align(Alignment.Center),
    //                        .padding(4.dp),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MinimalDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )

                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Dismiss")
                }
        }
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

//Funksjoner som bruker Zellers Algoritme til å finne første dag i mnd og år.
//firstDayOfMonth tar inn år som streng, mnd som int, returnerer navn på dag som String
fun monthIfJanOrFeb(month: Int, year: Int): Pair<Int, Int> {
    var updatedMonth = month
    var updatedYear = year

    when (month) {
        1, 2 -> {
            updatedMonth += 12
            updatedYear -= 1
        }
    }
    return Pair(updatedMonth, updatedYear)
}
fun doubleToInt(doubleNumber: Double): Int {
    return doubleNumber.toInt()
}
fun firstDayOfMonth(year: Int, month: Int): String {
    val monthUpdatet = monthIfJanOrFeb(month, year).first
    val yearUpdated = monthIfJanOrFeb(month, year).second
    val yearAsString = yearUpdated.toString()
    val firstPartOfYear = yearAsString.substring(0,2).toInt()
    val lastPartOfYear = yearAsString.substring(2,4).toInt()
    val firstOfMonth = 1
    val addIntegerParts =
        doubleToInt(2.6*monthUpdatet-5.39) + (lastPartOfYear/4) + (firstPartOfYear/4) +
                firstOfMonth + lastPartOfYear - (2*firstPartOfYear)
    val remainder = (addIntegerParts % 7 + 7) % 7
    val nameOfFirstDay = when (remainder) {
        0 -> "Sunday"
        1 -> "Monday"
        2 -> "Tuesday"
        3 -> "Wednesday"
        4 -> "Thursday"
        5 -> "Friday"
        else -> "Saturday"
    }
    return nameOfFirstDay
}

//Return True if year is divisible by 4 and reminder not zero when divided by 100, or if
// reminder of year divided by 400 = 0
fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}
fun numberOfDays(year: Int, month: Int):Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        2 -> if (isLeapYear(year)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> throw IllegalArgumentException("Invalid month")
    }
}
fun listOfDaysInMonth(year: Int, month: Int): List<String> {
    val possibleDaysInMonth = listOf(" ", " ", " ", " ", " ", " ", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
        "28", "29", "30", "31")
    val nameOfFirstDay = firstDayOfMonth(year, month)
    val firstDayIndex: Int = when(nameOfFirstDay) {
        "Monday" -> 6
        "Tuesday" -> 5
        "Wednesday" -> 4
        "Thursday" -> 3
        "Friday" -> 2
        "Saturday" -> 1
        else -> 0
    }
    val daysInChosenMonth = numberOfDays(year, month)
    val lastDayIndex = possibleDaysInMonth.indexOf(daysInChosenMonth.toString())
    val listOfDaysInChosenMonth = possibleDaysInMonth.subList(firstDayIndex, lastDayIndex+1)
    return listOfDaysInChosenMonth
}

@Composable
fun BackGround() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.calender_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize())
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalenderAppTheme {
        CalenderInformation()
    }
}