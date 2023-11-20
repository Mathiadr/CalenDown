package no.gruppe02.hiof.calendown

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.gruppe02.hiof.calendown.screen.createaccount.CreateAccountScreen
import no.gruppe02.hiof.calendown.screen.home.HomeScreen
import no.gruppe02.hiof.calendown.screen.login.LoginScreen
import no.gruppe02.hiof.calendown.screen.notifications.NotificationsScreen
import no.gruppe02.hiof.calendown.screen.profile.ProfileScreen
import no.gruppe02.hiof.calendown.screen.addEvent.AddEventScreen
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
    object EventDetails : Screen(
        route = "${EVENT_DETAIL}$EVENT_ID_ARG",
        R.string.event)
    object LogIn : Screen("Log in", R.string.log_in)
    object CreateAccount : Screen("Create account", R.string.create_account)
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

    // Set to false to hide bottom nav
    val bottomNavigationState = rememberSaveable{(mutableStateOf(true))}

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                bottomNavigationScreens = bottomNavigationScreens,
                bottomNavigationState = bottomNavigationState
            )},

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.LogIn.route,
            modifier = Modifier.padding(innerPadding)) {

            composable(Screen.Home.route) {
                HomeScreen(onEventClick = { eventId ->
                    val route = "${EVENT_DETAIL}?$EVENT_ID=$eventId"
                    navController.navigate(route)
                },
                    onAddEventClick = { navController.navigate(Screen.AddEvent.route)},
                    )
            }
            composable(Screen.LogIn.route) {
                LoginScreen(
                    loggedIn = { navController.navigate(Screen.Home.route) },
                    createAccount = { navController.navigate(Screen.CreateAccount.route)}
                )
            }
            composable(Screen.CreateAccount.route) {
                CreateAccountScreen(loggedIn = { navController.navigate(Screen.Home.route) })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
            composable(Screen.AddEvent.route) {
                AddEventScreen(onSaveEventClick = { navController.navigate(Screen.Home.route) })
            }

            composable(
                route = Screen.EventDetails.route,
                arguments = listOf(navArgument(EVENT_ID) {
                    nullable = false})
            ) {
                EventDetailScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar (
    navController: NavHostController,
    bottomNavigationScreens: List<Screen>,
    bottomNavigationState: MutableState<Boolean>) {
    // Holder på hvilket element i bottomNav som er selected
    var selectedBottomNavigationIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screen.Home.route ->{
            bottomNavigationState.value = true
        }
        Screen.LogIn.route ->{
            bottomNavigationState.value = false
        }
        Screen.CreateAccount.route ->{
            bottomNavigationState.value = false
        }
        Screen.Profile.route ->{
            bottomNavigationState.value = true
        }
        Screen.Notifications.route->{
            bottomNavigationState.value = true
        }
        Screen.AddEvent.route ->{
            bottomNavigationState.value = true
        }
        Screen.EventDetails.route ->{
            bottomNavigationState.value = true
        }
    }

    AnimatedVisibility(
        visible = bottomNavigationState.value) {

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 0.dp
        ) {
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
                    }
                )
            }
        }
    }
}
