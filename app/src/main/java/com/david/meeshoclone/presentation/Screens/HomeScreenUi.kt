package com.david.meeshoclone.presentation.Screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.david.meeshoclone.R
import com.david.meeshoclone.domain.models.ProductsDataModels
import com.david.meeshoclone.presentation.ViewModels.ShoppingAppViewModel
import com.david.meeshoclone.presentation.components.DetailedProductCard
import com.david.meeshoclone.presentation.components.DetailedProductItem
import com.david.meeshoclone.presentation.components.HorizontalCarousel
import com.david.meeshoclone.presentation.components.LowPriceCard
import com.david.meeshoclone.presentation.components.LowPriceCardItem
import com.david.meeshoclone.presentation.components.SearchBarComponentForHomeScreen
import com.david.meeshoclone.presentation.components.TopBarComponent
import com.david.meeshoclone.presentation.navigation.Routes
import okhttp3.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    val scrollBehaviour = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        snapAnimationSpec = spring(
            stiffness = Spring.StiffnessMedium
        )
    )

    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    // This is for Suggested for you option
    val getAllSuggestedProduct = viewModel.getAllProductsState.collectAsStateWithLifecycle()
    val getAllSuggestedProductData: List<ProductsDataModels> =
        getAllSuggestedProduct.value.UserData.orEmpty().filterNotNull()


    LaunchedEffect(key1 = Unit) {
        viewModel.getAllSuggestedProduct()
    }

    if (homeState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (homeState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = homeState.errorMessage!!)
        }
    } else {

        val items = listOf(
            LowPriceCardItem(
                image = 1,
                price = "$299",
                cardName = "Kids Boys"
            ),
            LowPriceCardItem(
                image = 1,
                price = "$299",
                cardName = "Kids Girls"
            ),
            LowPriceCardItem(
                image = 1,
                price = "$299",
                cardName = "Kids Clothing"
            ),
            LowPriceCardItem(
                image = 1,
                price = "$299",
                cardName = "Jewellery"
            ),
            LowPriceCardItem(
                image = 1,
                price = "$299",
                cardName = "women Sarees"
            )
        )

        val productItems = listOf(
            DetailedProductItem(
                image = R.drawable.boy,
                productName = "Kids Wear",
                productPrice = "289",
                discountPrice = "248",
                discount = "20% Off",
                rating = "4.4",
                ratingCount = "(12,774)"
            ),
            DetailedProductItem(
                image = R.drawable.boy,
                productName = "Kids Wear",
                productPrice = "289",
                discountPrice = "248",
                discount = "20% Off",
                rating = "4.4",
                ratingCount = "(12,774)"
            ),
            DetailedProductItem(
                image = R.drawable.boy,
                productName = "Kids Wear",
                productPrice = "289",
                discountPrice = "248",
                discount = "20% Off",
                rating = "4.4",
                ratingCount = "(12,774)"
            ),
            DetailedProductItem(
                image = R.drawable.boy,
                productName = "Kids Wear",
                productPrice = "289",
                discountPrice = "248",
                discount = "20% Off",
                rating = "4.4",
                ratingCount = "(12,774)"
            ),
            DetailedProductItem(
                image = R.drawable.boy,
                productName = "Kids Wear",
                productPrice = "289",
                discountPrice = "248",
                discount = "20% Off",
                rating = "4.4",
                ratingCount = "(12,774)"
            )
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopBarComponent(scrollBehaviour, navController)
                    SearchBarComponentForHomeScreen(navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(innerPadding)
            ) {
                item {
                    //Categories Section
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        // Add a "View All" item at the end
                        item {
                            ViewAllCategoryItem(
                                onClick = {
                                    navController.navigate(Routes.CategoryScreen)
                                }
                            )
                        }

                        items(homeState.categories ?: emptyList()) { category ->
                            CategoryItem(
                                imageUrl = category.categoryImage,
                                category = category.name,
                                onClick = {
                                    navController.navigate(
                                        Routes.EachCategoryItemScreen(
                                            categoryName = category.name
                                        )
                                    )
                                }
                            )
                        }

                    }
                }

                item {
                    HorizontalCarousel()

                    //Flash Sale Section
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Flash Sale",
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 20.sp
                            )
                            Text(
                                "See More",
                                color = colorResource(R.color.orange),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.clickable {
                                    navController.navigate(Routes.SeeAllProductsScreen)
                                }
                            )
                        }
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(homeState.products ?: emptyList()) { product ->
                                ProductCard(
                                    product = product,
                                    navController = navController
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "Low Price store",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(items) { cardData ->
                                LowPriceCard(cardData)
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

                        // If odd number of items , add empty Empty Space
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ViewAllCategoryItem(onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = colorResource(R.color.gray)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Category,
                modifier = Modifier.padding(8.dp),
                contentDescription = "View All",
                tint = Color.Unspecified
            )
        }
        Text("All", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CategoryItem(
    imageUrl: String,
    category: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, CircleShape)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
        Text(category, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ProductCard(product: ProductsDataModels, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                navController.navigate(Routes.EachProductDetailsScreen(productId = product.productId))
            }
            .aspectRatio(0.7f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .width(100.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$${product.finalPrice}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "(${product.availableUnits} left)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
