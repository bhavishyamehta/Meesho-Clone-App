package com.david.meeshoclone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.david.meeshoclone.R
import com.david.meeshoclone.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(scrollBehavior: TopAppBarScrollBehavior, navController: NavController) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        title = {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Hello,",
                    color = Color.Black,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
                Text(
                    text = "David",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 16.sp
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.ProfileScreen)
                }
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    tint = Color.Unspecified,
                    contentDescription = "Profile",
                    modifier = Modifier.size(34.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.WishlistScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favourite",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    Icons.Default.NotificationsNone,
                    contentDescription = "Notification",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Routes.CartScreen)
                }
            ) {
                Icon(
                    Icons.Default.ShoppingCartCheckout,
                    contentDescription = "Cart",
                    modifier = Modifier.size(22.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}