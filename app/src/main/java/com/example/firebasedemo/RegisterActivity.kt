package com.example.firebasedemo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.firebasedemo.ui.theme.FirebaseDemoTheme
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        setContent {
            FirebaseDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterScreen(firebaseAuth)
                }
            }
        }
    }

    private fun registerUser(userName: String, password: String, context: Context) {
        firebaseAuth.createUserWithEmailAndPassword(userName, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Registration failed
                    val exception = task.exception
                    Toast.makeText(context, "Registration Failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RegisterScreen(firebaseAuth: FirebaseAuth) {
        var textState by remember { mutableStateOf(TextFieldValue("")) }
        var textState1 by remember { mutableStateOf(TextFieldValue("")) }
        val context = LocalContext.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Compose App") }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = textState,
                            onValueChange = { textState = it },
                            label = { Text("Enter your name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = textState1,
                            onValueChange = { textState1 = it },
                            label = { Text("Enter your password") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        Spacer(modifier = Modifier.height(100.dp))
                        Button(onClick = {
                            val userName = textState.text
                            val password = textState1.text
                            when {
                                userName.isEmpty() || password.isEmpty() -> {
                                    Toast.makeText(context, "The credentials are empty", Toast.LENGTH_SHORT).show()
                                }
                                password.length < 6 -> {
                                    Toast.makeText(context, "Password is too short.", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    registerUser(userName, password, context)
                                }
                            }
                        }) {
                            Text("Register")
                        }
                    }
                }
            }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    FirebaseDemoTheme {
//        Greeting2()
//    }
//}