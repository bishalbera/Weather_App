package com.bishal.iosthemedweatherapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bishal.iosthemedweatherapp.domain.weather.WeatherData
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyWeatherDisplay(
weatherData: List<WeatherData>
) {


    LazyRow(
        modifier = Modifier
        .fillMaxWidth()
    ){
        items(weatherData.size){ index ->
        HourlyCard(weatherData[index])

        }


    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyCard(
    weatherData: WeatherData
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }

    Surface(
        modifier = Modifier
            .clip(shape = CircleShape)
            .height(100.dp)
            .width(46.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xff5936B4),
                        Color(0xff362A84)

                    )
                )
            )
    ) {
        Column {
            Text(
                text = formattedTime,
                color = Color.White
            )
            Image(
                painter = painterResource(id = weatherData.weatherType.iconRes),
                contentDescription = null,
                modifier = Modifier.width(40.dp)
            )
            Text(
                text = "${weatherData.temperatureCelsius}°C",
                color = Color.White,
                fontWeight = FontWeight.Thin
            )
        }


    }

}