package com.david.meeshoclone.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.david.meeshoclone.R
import com.david.meeshoclone.presentation.Screens.utils.Banner
import com.david.meeshoclone.presentation.ViewModels.ShoppingAppViewModel
import com.david.meeshoclone.presentation.components.DetailedProductCard
import com.david.meeshoclone.presentation.components.DetailedProductItem
import com.david.meeshoclone.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeeshoMallScreen(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("sort", "Category", "Gender", "Filters")

    val productItems = listOf(
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountPrice = "248",
            discount = "20% OFF",
            rating = "4.5",
            ratingCount = "(12,404)"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MEESHO MALL",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchBarScreen) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.WishlistScreen) }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Fav"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "cart"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    homeState.banner?.let { banner ->
                        Banner(banners = banner)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(R.color.lightYellow))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Budget Buys",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "View All",
                            color = Color.Red
                        )

                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowRight,
                                contentDescription = "View All Products",
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(color = Color.Red, shape = CircleShape)
                            )
                        }
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(productItems) { product ->
                            DetailedProductCard(
                                product = product,
                                modifier = Modifier.width(160.dp)
                            )
                        }
                    }
                }
            }

            item {
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            modifier = Modifier.background(color = Color.White),
                            shape = SegmentedButtonDefaults.itemShape(
                                baseShape = RoundedCornerShape(0.dp),
                                index = index,
                                count = options.size
                            ),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex,
                            label = { Text(text = label) }
                        )
                    }
                }
            }

            items(productItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rowItems.forEach { product ->
                        DetailedProductCard(
                            product,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    //If odd number of items, add empty space
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}