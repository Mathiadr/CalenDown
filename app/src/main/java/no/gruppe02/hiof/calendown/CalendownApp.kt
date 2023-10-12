package no.gruppe02.hiof.calendown

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.gruppe02.hiof.calendown.screen.AddEventScreen
import no.gruppe02.hiof.calendown.screen.HomeScreen
import no.gruppe02.hiof.calendown.screen.NotificationsScreen
import no.gruppe02.hiof.calendown.screen.ProfileScreen
import no.gruppe02.hiof.calendown.screen.eventdetail.EventDetailScreen

sealed class Screen(
    val route: String,
    @StringRes
    val title: Int,
    // Property er optional
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null) {

    object Home : Screen("Home", R.string.home, Icons.Filled.Home, Icons.Outlined.Home)
    object Profile : Screen("Profile", R.string.profile, Icons.Filled.Person, Icons.Outlined.Person)
    object Notifications : Screen("Notifications", R.string.notifications, Icons.Filled.Notifications, Icons.Outlined.Notifications)
    object AddEvent : Screen("Add Event", R.string.add_event)
    object EventDetails : Screen("Event Detail", R.string.event)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendownApp() {
    val navController = rememberNavController()

    val bottomNavigationScreens = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.Notifications
    )
    //Test
    // When you navigate to another screen, this value is updated.
    val currentRoute = navController
        .currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                bottomNavigationScreens = bottomNavigationScreens
            )},
        floatingActionButton = {
            // Vis FAB om du er på HomeScreen
            when (currentRoute.value?.destination?.route) {
                Screen.Home.route -> {
                    OpenAddEventScreen(
                        navController = navController,
                    )
                }
            }
        }
    ) { padding ->
        val mod = Modifier.padding(padding)

        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
            composable(Screen.AddEvent.route) {
                AddEventScreen()
            }
            composable(Screen.EventDetails.route) {
                EventDetailScreen()
            }
        }
    }
}

@Composable
fun OpenAddEventScreen(
    navController: NavHostController
) {
    FloatingActionButton(
        onClick = { navController.navigate(Screen.AddEvent.route)})
    {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Create new event button"
        )
    }
}

@Composable
fun BottomNavigationBar (
    navController: NavHostController,
    bottomNavigationScreens: List<Screen>) {
    // Should be stored in ViewModel??
    // Holder på hvilket element i bottomNav som er selected
    var selectedBottomNavigationIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        //TODO: Implementer funksjonalitet for å håndtere oppsamling i BackStackEntry
        //val navBackStackEntry by navController.currentBackStackEntryAsState()
        //val currentDestination = navBackStackEntry?.destination?.route


        bottomNavigationScreens.forEachIndexed { index, screen ->
            val title = stringResource(screen.title)
            NavigationBarItem(
                selected = selectedBottomNavigationIndex == index,
                onClick = {
                    selectedBottomNavigationIndex = index
                    navController.navigate(screen.route)
                },
                label = {
                    Text(text = title)
                },
                // https://www.youtube.com/watch?v=c8XP_Ee7iqY&t=526s
                // Lånt logikk for å få filled eller outlined icon basert på
                // hva som er selected
                icon = {
                    (if (index == selectedBottomNavigationIndex) {
                        screen.selectedIcon
                    } else
                        screen.unselectedIcon)?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = title)
                    }
                })
        }
    }
}
