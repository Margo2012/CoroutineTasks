package com.example.coroutinetasks


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coroutinetasks.ui.theme.CoroutineTasksTheme

class MainActivity : ComponentActivity() {
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   MainScreen(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {

    val scope = rememberCoroutineScope()
    var loadData by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadingState.collect {
            loadData += "$it\n"
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Загруженные данные:",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(text = loadData)
        Button(onClick = { viewModel.startLoading() }) {
            Text("Начать загрузку")
        }
        Button(onClick = { viewModel.stopLoading() }) {
            Text("Остановить загрузку")
        }
    }
}

