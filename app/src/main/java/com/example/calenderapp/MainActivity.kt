package com.example.calenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
//import com.example.calenderapp.ui.theme.CalenderAppTheme
//import com.example.compose.CalenderAppTheme
import com.example.compose.DarkColors
import com.example.compose.LightColors


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalenderAppTheme {
                CalenderInformation(monthNumber = 1, year = 2024)
            }
        }
    }
}

@Composable
fun CalenderAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }


    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

@Composable
fun ThemeToggleButton(useDarkTheme: Boolean, onToggle: (Boolean) -> Unit) {
    Button(onClick = {onToggle(!useDarkTheme) }) {
        Text(text = if (useDarkTheme) "Switch to Light mode" else "Switch to Dark mode")
    }
}

@Composable
fun CalenderInformation(monthNumber: Int = 2, year: Int = 2023) {
    var clicked by remember { mutableStateOf(false) } // Flyttet clicked hit
    var selectedItem by remember { mutableStateOf(0) }
    val isDarkTheme = isSystemInDarkTheme()
    val (darkTheme, setDarkTheme) = remember { mutableStateOf(isDarkTheme)}
    val background = if (darkTheme) { 
        R.drawable.darkmode_background} 
    else {  R.drawable.lightmode_background
    }

    CalenderAppTheme(useDarkTheme = darkTheme) {

    Box(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 50.dp, bottom = 265.dp)

    ) {
        Image(
            modifier =Modifier.fillMaxSize(),
            painter = painterResource(id = (background)),
            contentDescription = "Background image",
            contentScale = ContentScale.Crop,
        )


        Column {
            Row(
                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer))
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = showMonth(monthNumber = monthNumber),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp
                )
                Text(
                    text = year.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 22.sp
                )
            }

            // function to initialize calender - fra chatgpt
            CalendarLayout(year, monthNumber, onCardClick = { clickedItem ->
                clicked = true
                selectedItem = clickedItem
            })

            if (clicked) {
                PopupDialog(selectedItem, monthNumber, year, onDismissRequest = { clicked = false })
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
//                    .padding(start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer))
                    .height(60.dp)
                    .padding(top = 2.dp)
            ) {
                Text(
                    text = stringResource(R.string.bottom_click_text),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary


                    )
            }
            ThemeToggleButton(useDarkTheme = darkTheme, onToggle = setDarkTheme)
        }
    }
}
}// DARKMODE

@Composable
fun calculateWeeks(year: Int = 2024, month: Int = 9): Int {
    val startingDay = firstDayOfMonth(year, month)
    val numOfDays = numberOfDays(year, month)
    val remainderWeeks = (numOfDays % 7)

    return if (startingDay == "Sunday" || startingDay == "Saturday" && remainderWeeks > 1) {
        6
    } else if (startingDay == "Monday" && remainderWeeks == 0) {
        4
    } else 5
}

@Composable
fun WeekDays() {
    val listOfDays = listOf("", "mon", "tue", "wed", "thu", "fri", "sat", "sun")
    LazyRow(
        modifier = Modifier
//            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer))
            .height(46.dp)
            .width(46.dp)


    ) {
        items(count = listOfDays.size) { index ->
            Box(
                modifier = Modifier
                    .border(0.dp, MaterialTheme.colorScheme.primaryContainer)
                    .height(46.dp)
                    .width(48.dp),


            ) {
                Text(text = listOfDays[index],
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun WeekNumbers(numOfWeeks: Int = 5) {
//    val listOfWeeks = listOf("1", "2", "3", "4", "5") // Dette skal bli parameterisert
    LazyColumn(
//        contentPadding = PaddingValues(start = 5.dp),
    ) {
        items(count = numOfWeeks) { index ->
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.primaryContainer)
                    .height(46.dp)
                    .width(48.dp)
            ) {
                Text(
                    text = (index + 1).toString(),
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun CalendarLayout(year: Int, month: Int, onCardClick: (Int) -> Unit) {
//    var clicked by remember { mutableStateOf(false) }

    Box {
        Column {
            WeekDays()

            Row {
                WeekNumbers(calculateWeeks(year, month))
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = GridCells.Fixed(count = 7),
//                    contentPadding = PaddingValues(end = 5.dp)
                ) {

                    itemsIndexed(
                        items =  listOfDaysInMonth(year, month)
                    ) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 0.dp,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                                .height(height = 46.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onCardClick(item.toInt()) }
                            ) {

                                Text(
                                    text = item, // Viser tallene i lista øverst
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    //                        .padding(4.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
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
fun PopupDialog(
    date: Int = 3,
    numMonth: Int = 3,
    year: Int = 2024,
    onDismissRequest: () -> Unit,
) {
    val days = daysSinceJanuaryFirst(date, numMonth, year)
    Dialog(onDismissRequest = { onDismissRequest() }) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.5f)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            )


        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val alphaMonth = showMonth(monthNumber = numMonth)
                val dayOrDays = if (date == 2) "day" else "days"
                Text(

                    text = "$date.$alphaMonth is $days $dayOrDays since 1.January ",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary)

                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Dismiss",
                        color = MaterialTheme.colorScheme.onPrimary)
                }
        }
        }
    }
}

@Composable
fun TurnOnDarkMode(
    darkMode: Boolean,
    onDarkMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp)
            .wrapContentWidth(Alignment.End)
    ) {
        Text ( text = "Dark Mode",
                modifier = modifier.padding(end = 30.dp, top = 10.dp))
        Switch(
            checked = darkMode,
            onCheckedChange = onDarkMode
        )
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

//Calculate number of days since January 1st.
fun daysSinceJanuaryFirst(date: Int, month: Int, year: Int): String {
    //list with number of days in each month
    val daysInMonth = listOf(31, if (isLeapYear(year)) 29 else 28, 31, 30, 31, 30, 31,
        31, 30, 31, 30, 31)

    var daycount = 0

    for (m in 1 until month) {   //iterate in range m to current month
        daycount += daysInMonth[m - 1]     //add value from daysInMonth for previous months
    }
    daycount += date                       //add number  of days in current month to days

    return (daycount - 1).toString()
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalenderAppTheme {
        CalenderInformation(monthNumber = 1, year = 2024)
    }
}