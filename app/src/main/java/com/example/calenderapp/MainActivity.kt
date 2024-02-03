package com.example.calenderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
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
        ){
            Text(
                text = showMonth(monthNumber = monthNumber),
                fontWeight = FontWeight.Bold,
                color = Color.Green,
            )
        }
        // function to initialize calender
        CalendarLayout(onCardClick = { clicked = true })
        if (clicked) {
            MinimalDialog(onDismissRequest = { clicked = false})
        }

//        CalendarLayout(onCardClick = { clicked = true }) // Lagt til ny parameter
//        if (clicked) {
//            MinimalDialog(onDismissRequest = { clicked = false })

        Row (verticalAlignment = Alignment.Top,
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
fun CalendarLayout(onCardClick: () -> Unit) {
//    var clicked by remember { mutableStateOf(false) }
    Box(
    modifier = Modifier
) {//background for calender
//    Image(
//        painter = painterResource(R.drawable.calender_background),
//        contentDescription = null,
//        modifier = Modifier
//            .size(600.dp)
//            .padding(bottom = 200.dp)
//
//
//    )


    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 4.dp, color = Color.Blue),
        columns = GridCells.Fixed(count = 6),
        verticalArrangement = Arrangement.spacedBy(space = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 2.dp),
        contentPadding = PaddingValues(all = 8.dp)
    ) {

        itemsIndexed(items = listOf(13, 63, 22, 23, 66, 74)) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 46.dp)
                    .border(width = 2.dp, color = Color.Blue),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ){
                Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onCardClick() }
            ){

                Text(
                    text = item.toString(), // Viser tallene i lista øverst
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(4.dp),
                    color = Color.Black)
                }
            }
//            if (clicked) {
//                MinimalDialog {
//                    testClickable(1, "wow")
//                }
//            }
        }
    }

}}

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



fun testClickable(index: Int, content: String) {
    // tester om clickable fungerer
    println("clicked: $index")
    println(content)

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
fun firstDayOfMonth(year: String, month: Int): String {
    val yearAsInt = year.toInt()
    val monthUpdatet = monthIfJanOrFeb(month, yearAsInt).first
    val yearUpdated = monthIfJanOrFeb(month, yearAsInt).second
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalenderAppTheme {
        CalenderInformation()
    }
}