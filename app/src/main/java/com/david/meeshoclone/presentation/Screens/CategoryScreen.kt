package com.david.meeshoclone.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.david.meeshoclone.R
import com.david.meeshoclone.presentation.components.CategoryProductCard
import com.david.meeshoclone.presentation.components.CategoryProductItem
import com.david.meeshoclone.presentation.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController) {
    val categories = listOf(
        Category("Popular", R.drawable.boy),
        Category("Kurti, Saree & Lehenga", R.drawable.girl),
        Category("Women Western", R.drawable.girl2),
        Category("Men", R.drawable.akshay_kumar),
        Category("Women", R.drawable.disha_patani),
        Category("Kids", R.drawable.boy),
        Category("Seasonal", R.drawable.kartik_aaryan),
        Category("Home & Kitchen", R.drawable.boy),
        Category("Kurta", R.drawable.ajay_devgn),
        Category("Perfume", R.drawable.bhuvan_bam),
        Category("Shirts", R.drawable.carryminati),
    )

    val allProducts = listOf(
        CategoryProductItem(R.drawable.boy, "Kids Wear", "Popular"),
        CategoryProductItem(R.drawable.boy, "Girls Wear", "Popular"),
        CategoryProductItem(R.drawable.boy, "Mens Wear", "Men"),
        CategoryProductItem(R.drawable.boy, "Kurtis & Dress", "Kurti, Saree & Lehenga"),
        CategoryProductItem(R.drawable.boy, "Women Suits", "Kurti, Saree & Lehenga"),
        CategoryProductItem(R.drawable.boy, "Wedding Dress", "Kurti, Saree & Lehenga"),
        CategoryProductItem(R.drawable.boy, "Party Dress", "Kurti, Saree & Lehenga"),
        CategoryProductItem(R.drawable.boy, "Engagement\n Dress", "Kurti, Saree & Lehenga"),
        CategoryProductItem(R.drawable.boy, "Jeans", "Women Western"),
        CategoryProductItem(R.drawable.boy, "Shirts", "Men"),
        CategoryProductItem(R.drawable.boy, "Saree", "Women"),
        CategoryProductItem(R.drawable.boy, "Shirts & Pants", "Kids"),
        CategoryProductItem(R.drawable.boy, "Summer", "Seasonal"),
        CategoryProductItem(R.drawable.boy, "Mixer", "Home & Kitchen"),
        CategoryProductItem(R.drawable.boy, "Stove", "Home & Kitchen"),
        CategoryProductItem(R.drawable.boy, "Kurta's", "Kurts"),
        CategoryProductItem(R.drawable.boy, "Perfumes", "Perfume"),
        CategoryProductItem(R.drawable.boy, "Shirts", "Shirts"),
    )

    val categoryIndexMap = mutableMapOf<String, Int>()
    var itemIndex = 0

    val sections = categories.map { category ->
        categoryIndexMap[category.name] = itemIndex
        val filteredProducts = allProducts.filter { it.CategoryName == category.name }
        itemIndex += 2 // 2 for header , rest for products
        CategorySection(category, filteredProducts)
    }

    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedCategory) {
        categoryIndexMap[selectedCategory.name]?.let { itemIndex ->
            coroutineScope.launch {
                listState.animateScrollToItem(itemIndex)
            }
        }
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CATEGORIES",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchBarScreen) }) {
                        Icon(
                            Icons.Default.Search,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.WishlistScreen) }) {
                        Icon(
                            Icons.Default.HeartBroken,
                            modifier = Modifier.size(22.dp),
                            contentDescription = "favourite"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "cart"
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            //LeftSideBar With Category
            CategorySideBar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            //Main Content// Right: Product Section
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                sections.forEach { section ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = section.category.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                thickness = 1.5.dp,
                                color = colorResource(R.color.gray)
                            )
                        }
                    }
                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(
                                    min = 100.dp,
                                    max = 500.dp
                                ), // Restrict Height or Use Lazy Row for horizontal scrolling
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = false  // Let main column handle scroll
                        ) {
                            items(section.products) { product ->
                                CategoryProductCard(product = product)
                            }
                        }
                    }
                }
            }


        }
    }


}

@Composable
fun CategorySideBar(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .shadow(elevation = 4.dp)
            .width(80.dp)
            .fillMaxHeight()
            .background(Color.White)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )

        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.name,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.DarkGray,
                maxLines = 2,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Indicator on the Right
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(70.dp)
                    .background(
                        color = Color(0xFF28942B),
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    )
            )
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

data class CategorySection(
    val category: Category,
    val products: List<CategoryProductItem>
)


data class Category(
    val name: String,
    val iconRes: Int
)

//data class CategoryProductItem(
//    val imageRes: Int,
//    val categoryProductName: String,
//    val categoryName: String
//)