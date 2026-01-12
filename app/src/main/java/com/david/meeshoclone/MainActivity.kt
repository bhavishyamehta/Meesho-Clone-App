package com.david.meeshoclone

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.postDelayed
import com.david.meeshoclone.presentation.navigation.App
import com.david.meeshoclone.ui.theme.MeeshoCloneTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeeshoCloneTheme {
                MainScreen(firebaseAuth) { }
            }
        }
    }


    @Composable
    fun MainScreen(fAuth: FirebaseAuth, payTest: () -> Unit) {

        val showSplash = remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                showSplash.value = false
            }, 3000)
        }

        if (showSplash.value) {
            SplashScreen()
        } else {
            App(firebaseAuth = firebaseAuth, { payTest })
        }
    }


    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.meesho_logo),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Welcome to the Clothing Store",
                    style = MaterialTheme.typography.h3.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }

}
