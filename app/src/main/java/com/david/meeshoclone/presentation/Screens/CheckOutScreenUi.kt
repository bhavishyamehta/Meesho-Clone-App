package com.david.meeshoclone.presentation.Screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.david.meeshoclone.R
import com.david.meeshoclone.presentation.ViewModels.ShoppingAppViewModel
import com.david.meeshoclone.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavHostController,
    productId: String,
    pay: () -> Unit
) {
    val state = viewModel.getProductByIdState.collectAsStateWithLifecycle()
    val productData = state.value.UserData

    val email = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }
    val selectedMethod = remember { mutableStateOf("Standard Free Delivery Over Rs.4500") }

    LaunchedEffect(key1 = Unit) {
        viewModel.getProductById(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SHIPPING",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
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
                }
            )
        }
    ) { innerPadding ->
        when {
            state.value.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.value.ErrorMsg != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Sorry, Unable to Get Information")
                }
            }

            state.value.UserData == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No Products Available")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = state.value.UserData!!.image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .border(1.dp, Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = state.value.UserData!!.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = state.value.UserData!!.finalPrice,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text("Contact Information", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text("Shipping Address", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = country.value,
                            onValueChange = { country.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Country/Region") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            OutlinedTextField(
                                value = firstName.value,
                                onValueChange = { firstName.value = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                label = { Text("First Name") }
                            )
                            OutlinedTextField(
                                value = lastName.value,
                                onValueChange = { lastName.value = it },
                                modifier = Modifier
                                    .weight(1f),
                                label = { Text("Last Name") }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = address.value,
                            onValueChange = { address.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Address") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            OutlinedTextField(
                                value = city.value,
                                onValueChange = { city.value = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                label = { Text("City") }
                            )

                            OutlinedTextField(
                                value = postalCode.value,
                                onValueChange = { postalCode.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Postal Code") }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            Text("Shipping Method", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedMethod.value == "Standard FREE delivery over RS.4500",
                                    onClick = {
                                        selectedMethod.value = "Standard FREE delivery over RS.4500"
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Standard FREE delivery over RS.4500")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedMethod.value == "Cash on Delivery Rs.50",
                                    onClick = {
                                        selectedMethod.value = "Cash on Delivery Rs.50"
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cash on Delivery Rs.50")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                navController.navigate(Routes.PayScreen)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
                        ) {
                            Text(text = "Continue to Shipping")
                        }
                    }
                }
            }
        }
    }
}