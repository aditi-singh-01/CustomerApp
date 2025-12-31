package com.example.fooddeliveryapp.presentaion.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.List




data class RestaurantUiModel(
    val name: String,
    val rating: Double,
    val imageUrl: String
)


val dummyRestaurants = listOf(
    RestaurantUiModel(
        name = "Veggie Zone",
        rating = 4.0,
        imageUrl = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c"
    ),
    RestaurantUiModel(
        name = "Burger Hub",
        rating = 4.3,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    ),
    RestaurantUiModel(
        name = "Spice Garden",
        rating = 4.1,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    ),
    RestaurantUiModel(
        name = "Truffles",
        rating = 4.1,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    ),
    RestaurantUiModel(
        name = "Subway",
        rating = 4.1,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    ),
    RestaurantUiModel(
        name = "Meghna",
        rating = 4.1,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    ),
    RestaurantUiModel(
        name = "Nalapaka",
        rating = 4.1,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    ),
    RestaurantUiModel(
        name = "Meghna",
        rating = 4.1,
        imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349"
    )

)

@Composable
fun HomeScreen() {

    var selectedTab by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            HomeTopBar()
            HomeSearchBar()
            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTab) {
                BottomNavItem.Home -> {
                    // 👇 Your existing restaurant list
                    LazyColumn {
                        items(dummyRestaurants) { restaurant ->
                            RestaurantCard(restaurant)
                        }
                    }
                }

                BottomNavItem.Orders -> {
                    // 👇 Temporary Orders UI
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Orders Screen")
                    }
                }
            }
        }
    }
}



@Composable
fun RestaurantCard(restaurant: RestaurantUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🖼 IMAGE
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = restaurant.name,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // 📝 TEXT
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
            .padding( horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Address Section
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

        // Profile Icon
        IconButton(onClick = {  }) {
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
        placeholder = { Text("Search for restaurants or dishes") },
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
            label = { Text("Order List") }
        )
    }
}







