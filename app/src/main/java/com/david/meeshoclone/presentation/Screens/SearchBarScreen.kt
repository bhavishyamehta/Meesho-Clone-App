package com.david.meeshoclone.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.david.meeshoclone.R
import com.david.meeshoclone.presentation.components.OutLinedCardForSearchScreen
import com.david.meeshoclone.presentation.components.SearchBarComponentForSearchScreen

@Composable
fun SearchBarScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                SearchBarComponentForSearchScreen()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Popular Searches",
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .padding(top = 10.dp)
                ) {
                    OutLinedCardForSearchScreen(product = "saree")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "kurti")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "short kurti")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "tshirt")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "top for women")
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    OutLinedCardForSearchScreen(product = "kurti set")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "watch")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "earing")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "top")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "shoes")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "palazzo")
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    OutLinedCardForSearchScreen(product = "water bottle")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "slipper")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "bangles")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutLinedCardForSearchScreen(product = "tshirt for women")
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Image(
                    painter = painterResource(id = R.drawable.carryminati),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}