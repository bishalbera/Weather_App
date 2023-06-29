@file:OptIn(ExperimentalMaterialApi::class)

package com.bishal.iosthemedweatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bishal.iosthemedweatherapp.domain.weather.WeatherData
import com.bishal.iosthemedweatherapp.presentation.HourlyWeatherDisplay
import com.bishal.iosthemedweatherapp.presentation.WeatherState
import com.bishal.iosthemedweatherapp.presentation.WeatherViewModel
import com.bishal.iosthemedweatherapp.ui.theme.IosThemedWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,

        ))
        setContent {
            IosThemedWeatherAppTheme {
                 val scaffoldState = rememberBottomSheetScaffoldState(
                     bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
                 )

                val weatherData =
                BottomSheetScaffold(
                    sheetShape = RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp
                    ),
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 140.dp,
                    sheetContent = {
                        ShowWeatherDetails(
                            state = viewModel.state,
                            weatherData = weatherData

                        )
                    },
                    content = {

                    }
                )

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
     fun ShowWeatherDetails(
        state: WeatherState,
    weatherData: List<WeatherData>
     ) {
         Surface(
             modifier = Modifier
                 .fillMaxWidth()
                 .background(
                     brush = Brush.radialGradient(
                         listOf(
                             Color(0xffF7CBFD),
                             Color(0xff7758D1)

                         )
                     )
                 )
         ) {
             HourlyWeatherDisplay(weatherData = weatherData)


         }



    }
}

