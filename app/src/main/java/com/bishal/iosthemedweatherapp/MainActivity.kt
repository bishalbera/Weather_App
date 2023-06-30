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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

                val background = painterResource(id = R.drawable.evening)

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
                        BackgroundContent(
                            backgroundImage = background,
                            state = viewModel.state
                        )

                    }
                )

            }
        }
    }
@Composable
fun BackgroundContent(
    backgroundImage: Painter,
    state: WeatherState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.weatherInfo?.currentWeatherData?.let { data ->
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${data.temperatureCelsius}°C",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                    )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 20.sp,
                    color = Color.White
                )


            }


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

