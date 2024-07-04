package com.example.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LogoutScreen() }

    }

    @Composable
    fun LogoutScreen() {

        Button(onClick = {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, StartActivity::class.java))
            finishActivity(this)
        }) {
            Text(text = "Logout")
        }
    }

    @Preview
    @Composable
    fun Preview() {
        LogoutScreen()
    }
}