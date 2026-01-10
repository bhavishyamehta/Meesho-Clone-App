package com.david.meeshoclone.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.david.meeshoclone.R
import com.david.meeshoclone.domain.models.CartDataModels
import com.david.meeshoclone.presentation.ViewModels.ShoppingAppViewModel
import com.david.meeshoclone.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachProductDetailedScreen(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
    productID: String ?= null
) {
    val getProductById = viewModel.getProductByIdState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    var selectedSize by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(1) }
    var isFavourite by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getProductById(productID!!)
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Routes.SearchBarScreen) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notification",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(Routes.CartScreen) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        when {
            getProductById.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            getProductById.value.ErrorMsg != null -> {
                Text(text = getProductById.value.ErrorMsg!!)
            }

            getProductById.value.UserData != null -> {
                val product = getProductById.value.UserData!!.copy(productId = productID!!)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier.height(300.dp)
                    ) {
                        AsyncImage(
                            model = product.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rs ${product.price}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text(
                            text = "Size",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("S", "L", "XL").forEach { size ->
                                OutlinedButton(
                                    onClick = { selectedSize = size },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedSize == size) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        contentColor = if (selectedSize == size) Color.White else MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(size)
                                }
                            }
                        }

                        Text(
                            text = "Quantity",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.background(
                                color = colorResource(R.color.orange).copy(alpha = 0.8f),
                                shape = RoundedCornerShape(16.dp)
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Text(
                                    "-",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontSize = 36.sp,
                                    color = Color.White
                                )
                            }
                            Text(
                                quantity.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            IconButton(onClick = { quantity++ }) {
                                Text(
                                    "+",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color.White
                                )
                            }
                        }

                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        Text(text = product.description)

                        Button(
                            onClick = {
                                val cartDataModels = CartDataModels(
                                    name = product.name,
                                    image = product.image,
                                    price = product.price,
                                    quantity = quantity.toString(),
                                    size = selectedSize,
                                    productId = product.productId,
                                    description = product.description,
                                    category = product.category
                                )
                                viewModel.addToCart(CartDataModels = cartDataModels)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
                        ) {
                            Text("Add to cart")
                        }

                        Button(
                            onClick = {
                                navController.navigate(Routes.CheckOutScreen(productID))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
                        ) {
                            Text("Buy Now")
                        }

                        OutlinedIconButton(
                            onClick = {
                                isFavourite = !isFavourite
                                viewModel.addToFav(product)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row {

                                Icon(
                                    if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favourite",
                                )
                                Text("Add to wishlist")
                            }
                        }
                    }
                }
            }
        }
    }
}