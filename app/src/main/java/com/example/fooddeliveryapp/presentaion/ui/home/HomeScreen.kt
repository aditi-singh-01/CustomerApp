package com.example.fooddeliveryapp.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.collectAsState
import com.example.fooddeliveryapp.presentaion.viewmodel.HomeViewModel
import com.example.fooddeliveryapp.presentation.home.RestaurantUiModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
    //viewModel: CartViewModel
) {

    // observe UI state from viewmodel
    val uiState by viewModel.container.stateFlow.collectAsState() //uiState is defined in homeviewmodel

    // Stores the currently selected bottom tab
    var selectedTab by remember {
        mutableStateOf<BottomNavItem>(BottomNavItem.Home)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            HomeTopBar()
            HomeSearchBar()
            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTab) {

                //This will run only when HOME tab is selected
                BottomNavItem.Home -> { //
                    if (uiState.isLoading) { // if this will be true , loader will be shown means data is still being loaded
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {  //data is ready to be displayed On Screen(UI)
                        LazyColumn {
                            items(uiState.restaurants) { restaurant ->
                                RestaurantCard(restaurant)
                            }
                        }
                    }
                }

                BottomNavItem.Orders -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Orders Screen",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RestaurantCard(
    restaurant: RestaurantUiModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = restaurant.name,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "⭐ ${restaurant.rating}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = "Deliver to",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Home • Bangalore",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun HomeSearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(text = "Search for restaurants or dishes")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}

sealed class BottomNavItem(
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("Home", Icons.Default.Home)
    object Orders : BottomNavItem("Orders", Icons.Default.List)
}

@Composable
fun BottomNavBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = selectedItem == BottomNavItem.Home,
            onClick = { onItemSelected(BottomNavItem.Home) },
            icon = {
                Icon(
                    imageVector = BottomNavItem.Home.icon,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = selectedItem == BottomNavItem.Orders,
            onClick = { onItemSelected(BottomNavItem.Orders) },
            icon = {
                Icon(
                    imageVector = BottomNavItem.Orders.icon,
                    contentDescription = "Orders"
                )
            },
            label = { Text("Orders") }
        )
    }
}
