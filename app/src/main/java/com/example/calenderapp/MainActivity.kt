package com.example.calenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.compose.DarkColors
import com.example.compose.LightColors
import java.util.Calendar


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalenderAppTheme {
                CalendarLayout(monthNumber = 2, year = 2024)
            }
        }
    }
}

@Composable
fun CalenderAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
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
fun CalendarLayout(monthNumber: Int, year: Int) {
    var clicked by remember { mutableStateOf(false) } // Flyttet clicked hit
    var selectedItem by remember { mutableIntStateOf(0) }
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
            CalendarGrid(year, monthNumber, onCardClick = { clickedItem ->
                clicked = true
                selectedItem = clickedItem
            })

            if (clicked) {
                PopupDialog(selectedItem, monthNumber, year, onDismissRequest = { clicked = false })
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer))
                    .height(60.dp)
                    .padding(top = 2.dp)

            ) {
                Column {
                    Text(
                        text = stringResource(R.string.workdays_text) + " "
                                + workdaysInMonth(year, monthNumber),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    Text(
                        text = stringResource(R.string.days_since_jan1),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }
            ThemeToggleButton(useDarkTheme = darkTheme, onToggle = setDarkTheme)
        }
    }
}
}// DARKMODE

@Composable
fun WeekDays() {
    val listOfDays = listOf("", stringResource(R.string.monday),
        stringResource(R.string.tues_thursday), stringResource(R.string.wednesday),
        stringResource(R.string.tues_thursday), stringResource(R.string.friday),
        stringResource(R.string.sat_sunday), stringResource(R.string.sat_sunday))
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
                    .border(0.5.dp, MaterialTheme.colorScheme.primaryContainer)
                    .height(46.dp)
                    .width(48.dp),
            ) {
                Text(
                    text = listOfDays[index],
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun WeekNumbers(year: Int, month:Int) {
    val weeks = calculateWeekNumbers(year, month)
    LazyColumn {
        items(weeks.size) { index ->
            val weekNumber = weeks[index]
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.primaryContainer)
                    .height(46.dp)
                    .width(48.dp)
            ) {
                Text(
                    text = weekNumber.toString(),
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun calculateWeekNumbers(year: Int, month: Int): List<Int> {
    val calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1) // Calendar.MONTH starts from 0
        set(Calendar.DAY_OF_MONTH, 1)
    }

    val weeks = mutableListOf<Int>()   //list to hold week numbers
    val totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    for (dayOfMonth in 1..totalDaysInMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth) //set calendar object day to dayOgMonth
        val weekNumber = calendar.get(Calendar.WEEK_OF_YEAR) //get week number for current day
        //check if week number is different from last number, or if list empty
        if (weeks.isEmpty() || weeks.last() != weekNumber) {
            weeks.add(weekNumber)
        }
    }
    return weeks
}

@Composable
fun CalendarGrid(year: Int, month: Int, onCardClick: (Int) -> Unit) {
    Column {
        WeekDays()

        Row {
            WeekNumbers(year, month)
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(),
                columns = GridCells.Fixed(count = 7),
            ) {

                itemsIndexed(
                    items =  listOfDaysInMonth(year, month)
                ) { _, item ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 0.5.dp,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                                .height(height = 46.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            if (item != " ") {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable { onCardClick(item.toInt()) }
                            ) {
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .align(Alignment.Center),
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
fun ThemeToggleButton(useDarkTheme: Boolean, onToggle: (Boolean) -> Unit) {
    Button(onClick = {onToggle(!useDarkTheme) }) {
        Text(text = if (useDarkTheme) stringResource(R.string.light_mode) else stringResource(R.string.dark_mode))
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
                val dayOrDays = if (date == 2) stringResource(R.string.day) else stringResource(R.string.days)
                Text(
                    text = "$date.$alphaMonth is $days $dayOrDays since 1.January", //TODO()
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary)

                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text(
                        stringResource(R.string.dismiss),
                        color = MaterialTheme.colorScheme.onPrimary)
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

/** Functions that use Zeller's Algorithm to find the first day of the month and year.
firstDayOfMonth takes year as int, month as int, returns the name of the day as String**/

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
    val nameOfFirstDay = when ((addIntegerParts % 7 + 7) % 7) {
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

/** Return True if year is divisible by 4 and reminder not zero when divided by 100, or if
reminder of year divided by 400 = 0 **/
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
    val possibleDaysInMonth = listOf(
        " ", " ", " ", " ", " ", " ", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
        "23", "24", "25", "26", "27", "28", "29", "30", "31"
    )
    val firstDayIndex: Int = when (firstDayOfMonth(year, month)) {
        "Monday" -> 6
        "Tuesday" -> 5
        "Wednesday" -> 4
        "Thursday" -> 3
        "Friday" -> 2
        "Saturday" -> 1
        else -> 0
    }
    val daysInChosenMonth = numberOfDays(year, month)
    //Set last index equal to index in possibleDaysInMonth where value = daysInChosenMonth
    val lastDayIndex = possibleDaysInMonth.indexOf(daysInChosenMonth.toString())
    return possibleDaysInMonth.subList(firstDayIndex, lastDayIndex + 1)
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

@VisibleForTesting
internal fun workdaysInMonth(year: Int, month: Int): Int {
    val daysInMonth = numberOfDays(year, month) //total days in month
    val firstDayInMonth = firstDayOfMonth(year, month) //stringName of first day

    val weekdaysPattern = listOf(
        "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday"
    )

    // Find the index of the first day in the pattern
    val firstDayIndex = weekdaysPattern.indexOf(firstDayInMonth)

    // A list to hold all days as string
    val allDays = mutableListOf<String>()

    // Add the weekdays to the list based on the pattern
    for (i in 0 until daysInMonth) {
        val weekday = weekdaysPattern[(firstDayIndex + i) % 7] // Get the weekday based on the index
        allDays.add(weekday)
    }
    // Count all entries in the list, excluding Saturday and Sunday to get workdays
    return allDays.count { it != "Saturday" && it != "Sunday" }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalenderAppTheme {
        CalendarLayout(monthNumber = 12, year = 2024)
    }
}