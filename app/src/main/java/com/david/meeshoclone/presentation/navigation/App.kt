package com.david.meeshoclone.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.david.meeshoclone.R
import com.david.meeshoclone.presentation.Screens.AllCategoriesScreenUi
import com.david.meeshoclone.presentation.Screens.CartScreenUi
import com.david.meeshoclone.presentation.Screens.CategoryScreen
import com.david.meeshoclone.presentation.Screens.CheckOutScreenUi
import com.david.meeshoclone.presentation.Screens.EachCategoryProductScreen
import com.david.meeshoclone.presentation.Screens.EachProductDetailedScreen
import com.david.meeshoclone.presentation.Screens.GetAllFav
import com.david.meeshoclone.presentation.Screens.GetAllProducts
import com.david.meeshoclone.presentation.Screens.HomeScreenUi
import com.david.meeshoclone.presentation.Screens.LoginScreenUi
import com.david.meeshoclone.presentation.Screens.MeeshoMallScreen
import com.david.meeshoclone.presentation.Screens.MyOrderScreen
import com.david.meeshoclone.presentation.Screens.PayScreen
import com.david.meeshoclone.presentation.Screens.ProfileScreen
import com.david.meeshoclone.presentation.Screens.SearchBarScreen
import com.david.meeshoclone.presentation.Screens.SignUpScreen
import com.example.bottombar.AnimatedBottomBar
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    var shouldShowBottomBar by remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination) {
        shouldShowBottomBar = when (currentDestination) {
            Routes.HomeScreen::class.qualifiedName,
            Routes.CategoryScreen::class.qualifiedName,
            Routes.MyOrdersScreen::class.qualifiedName,
            Routes.MeeshoMallScreen::class.qualifiedName -> true

            else -> false
        }
    }

    val BottomNavItem = listOf(
        BottomNavItem(
            "Home",
            icon = Icons.Default.Home,
            unSelectedIcon = painterResource(id = R.drawable.ic_homee)
        ),
        BottomNavItem(
            "Category",
            icon = Icons.Default.Category,
            unSelectedIcon = painterResource(id = R.drawable.ic_victory)
        ),
        BottomNavItem(
            "Mall",
            icon = Icons.Default.LocalMall,
            unSelectedIcon = painterResource(id = R.drawable.ic_gift)
        ),
        BottomNavItem(
            "My Orders",
            icon = Icons.Default.ShoppingBag,
            unSelectedIcon = painterResource(id = R.drawable.ic_purchase)
        )
    )

    val startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            if (shouldShowBottomBar) {
                AnimatedBottomBar(
                    selectedItem = selectedItem,
                    itemSize = BottomNavItem.size,
                    containerColor = Color.White
                ) {
                    BottomNavItem.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (index) {
                                    0 -> navController.navigate(Routes.HomeScreen)
                                    1 -> navController.navigate(Routes.CategoryScreen)
                                    2 -> navController.navigate(Routes.MeeshoMallScreen)
                                    3 -> navController.navigate(Routes.MyOrdersScreen)
                                }
                            },
                            label = {
                                if (index == selectedItem) {
                                    Text(
                                        text = navigationItem.name,
                                        color = Color.Black,
                                        fontSize = 10.sp
                                    )
                                } else {
                                    Text(
                                        text = navigationItem.name,
                                        color = Color.Gray,
                                        fontSize = 10.sp
                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navigationItem.icon,
                                    contentDescription = navigationItem.name,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (index == selectedItem) {
                                        Color.Black
                                    } else {
                                        Color.Gray
                                    }
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (shouldShowBottomBar) 45.dp else 0.dp)
        ) {
            NavHost(navController = navController, startDestination = startScreen) {

                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUi(
                            navController = navController,
                        )
                    }

                    composable<Routes.SignUpScreen> {
                        SignUpScreen(
                            navController = navController
                        )
                    }
                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUi(
                            navController = navController,
                        )
                    }

                    composable<Routes.CategoryScreen> {
                        CategoryScreen(
                            navController = navController
                        )
                    }

                    composable<Routes.MeeshoMallScreen> {
                        MeeshoMallScreen(
                            navController = navController
                        )
                    }

                    composable<Routes.MyOrdersScreen> {
                        MyOrderScreen(
                            navController = navController
                        )
                    }

                    composable<Routes.ProfileScreen> {
                        ProfileScreen(
                            firebaseAuth = firebaseAuth, navController = navController
                        )
                    }

                    composable<Routes.WishlistScreen> {
                        GetAllFav(
                            navController = navController
                        )
                    }

                    composable<Routes.CartScreen> {
                        CartScreenUi(
                            navController = navController
                        )
                    }

                    composable<Routes.SearchBarScreen> {
                        SearchBarScreen(
                            navController = navController
                        )
                    }

                    composable<Routes.PayScreen> {
                        PayScreen(
                            navController = navController
                        )
                    }

                    composable<Routes.SeeAllProductsScreen> {
                        GetAllProducts(
                            navController = navController
                        )
                    }

                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUi(
                            navController = navController
                        )
                    }
                }

                composable<Routes.EachProductDetailsScreen> {
                    val product: Routes.EachProductDetailsScreen = it.toRoute()
                    EachProductDetailedScreen(
                        productID = product.productId,
                        navController = navController
                    )
                }

                composable<Routes.EachCategoryItemScreen> {
                    val category: Routes.EachCategoryItemScreen = it.toRoute()
                    EachCategoryProductScreen(
                        categoryName = category.categoryName,
                        navController = navController
                    )
                }

                composable<Routes.CheckOutScreen> {
                    val product: Routes.EachProductDetailsScreen = it.toRoute()
                    CheckOutScreenUi(
                        productId = product.productId,
                        navController = navController,
                        pay = payTest
                    )
                }
            }
        }
    }
}


data class BottomNavItem(
    val name: String,
    val icon: ImageVector,
    val unSelectedIcon: Painter
)