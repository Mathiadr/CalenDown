package no.gruppe02.hiof.calendown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import no.gruppe02.hiof.calendown.screen.AddEventScreen
import no.gruppe02.hiof.calendown.screen.HomeScreen
import no.gruppe02.hiof.calendown.screen.LoginScreen
import no.gruppe02.hiof.calendown.screen.eventdetail.EventDetailScreen
import no.gruppe02.hiof.calendown.ui.theme.CalenDownTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalenDownTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalendownApp()

                }
            }
        }
    }
}