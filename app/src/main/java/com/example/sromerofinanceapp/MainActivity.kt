package com.example.sromerofinanceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sromerofinanceapp.ui.theme.SRomeroFinanceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SRomeroFinanceAppTheme {
                HomeScreen(user = User("Pablo", 1500.0))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(user: User) {
    // Lista de ejemplo con 6 transacciones
    val sampleTransactions = listOf(
        Transaction("Starbucks", "Food & Drinks", -8.50, "09:30 AM", android.R.drawable.ic_menu_edit),
        Transaction("Walmart", "Groceries", -120.30, "12:15 PM", android.R.drawable.ic_menu_gallery),
        Transaction("Amazon", "Electronics", -299.99, "02:20 PM", android.R.drawable.ic_menu_camera),
        Transaction("Uber", "Fuel", -15.75, "06:45 PM", android.R.drawable.ic_menu_directions),
        Transaction("Refund", "Electronics", 299.99, "07:30 PM", android.R.drawable.ic_menu_revert),
        Transaction("McDonald's", "Dining", -12.40, "08:00 PM", android.R.drawable.ic_menu_agenda)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Hola ${user.name}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Bienvenido de nuevo",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Abrir menú */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Tarjetas resumen
            item {
                SummaryCardsSection()
            }

            // Encabezado de transacciones
            item {
                TransactionsHeader()
            }

            // Lista de transacciones
            items(sampleTransactions) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}

@Composable
fun SummaryCardsSection() {
    val cards = listOf(
        SummaryCard("Actividad de la Semana", 1250.0, Color(0xFF6200EE)),
        SummaryCard("Ventas totales", 5430.0, Color(0xFF03DAC5)),
        SummaryCard("Ganancias totales", 2100.0, Color(0xFFFFB74D))
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        cards.forEach { card ->
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .height(100.dp),
                colors = CardDefaults.cardColors(containerColor = card.color),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = card.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$${card.amount}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.titleLarge
        )
        TextButton(onClick = { /* Ver todas */ }) {
            Text("See All")
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono usando recurso drawable
        Icon(
            painter = painterResource(id = transaction.iconRes),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))

        // Información del establecimiento y categoría
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.establishment,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = transaction.category,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Monto y hora
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$${String.format("%.2f", transaction.amount)}",
                color = if (transaction.amount >= 0) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = transaction.time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SRomeroFinanceAppTheme {
        HomeScreen(user = User("Juan", 1500.0))
    }
}