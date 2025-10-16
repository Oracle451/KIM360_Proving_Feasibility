package com.example.databasedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.databasedemo.data.AppDatabase
import com.example.databasedemo.data.Repository
import com.example.databasedemo.ui.MainViewModel
import com.example.databasedemo.ui.MainViewModelFactory
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(applicationContext)
        val repo = Repository(db.entryDao())
        val vmFactory = MainViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]

        setContent {
            MaterialTheme {
                val textState by viewModel.text.collectAsState()
                MainScreen(textState) { viewModel.loadRandom() }
            }
        }
    }
}

@Composable
fun MainScreen(displayText: String, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
            Text("Show random sentence")
        }
    }
}
