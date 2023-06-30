@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bishal.iosthemedweatherapp.domain.weather.WeatherData
import com.bishal.iosthemedweatherapp.presentation.HourlyWeatherDisplay
import com.bishal.iosthemedweatherapp.presentation.WeatherForecast
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
    
     ) {
         Surface(
             modifier = Modifier
                 .fillMaxWidth()

         ) {
            WeatherForecast(state = state, modifier = Modifier.background( brush = Brush.radialGradient(
                listOf(
                    Color(0xffF7CBFD),
                    Color(0xff7758D1)

                )
            )))

         }



    }
}

