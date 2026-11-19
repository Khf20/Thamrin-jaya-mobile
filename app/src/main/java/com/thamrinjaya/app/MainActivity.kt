package com.thamrinjaya.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thamrinjaya.app.navigation.Screen
import com.thamrinjaya.app.theme.ThamrinJayaTheme
import com.thamrinjaya.app.theme.PageBackground
import com.thamrinjaya.app.ui.components.UnitSwitcherSheet
import com.thamrinjaya.app.ui.screens.AdminScreen
import com.thamrinjaya.app.ui.screens.CompanyProfileScreen
import com.thamrinjaya.app.ui.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThamrinJayaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PageBackground
                ) {
                    val navController = rememberNavController()
                    var showUnitSwitcher by remember { mutableStateOf(false) }

                    // Lebar isi dibatasi maks 600dp dan ditaruh di tengah, supaya di tablet,
                    // HP lipat, dan mode landscape tampilan tidak melebar berlebihan.
                    // Di HP biasa (lebar di bawah 600dp) tidak ada perubahan apa pun.
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .fillMaxHeight()
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigateToCompany = { company, tab ->
                                    navController.navigate(
                                        Screen.CompanyProfile.createRoute(company.id, tab)
                                    )
                                },
                                onNavigateToAdmin = {
                                    navController.navigate(Screen.Admin.route)
                                },
                                onOpenMenu = {
                                    showUnitSwitcher = true
                                }
                            )
                        }

                        composable(
                            route = Screen.CompanyProfile.route,
                            arguments = listOf(
                                navArgument("companyId") { type = NavType.StringType },
                                navArgument("tab") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) { backStackEntry ->
                            val companyId = backStackEntry.arguments?.getString("companyId") ?: "globalindo"
                            val initialTab = backStackEntry.arguments?.getInt("tab") ?: 0

                            CompanyProfileScreen(
                                companyId = companyId,
                                initialTab = initialTab,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onNavigateToAdmin = {
                                    navController.navigate(Screen.Admin.route)
                                },
                                onOpenMenu = {
                                    showUnitSwitcher = true
                                }
                            )
                        }

                        composable(Screen.Admin.route) {
                            AdminScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                    }

                    if (showUnitSwitcher) {
                        UnitSwitcherSheet(
                            onDismissRequest = {
                                showUnitSwitcher = false
                            },
                            onSelectHome = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route) { inclusive = true }
                                }
                            },
                            onSelectAdmin = {
                                navController.navigate(Screen.Admin.route)
                            },
                            onSelectCompany = { company, tab ->
                                navController.navigate(
                                    Screen.CompanyProfile.createRoute(company.id, tab)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
