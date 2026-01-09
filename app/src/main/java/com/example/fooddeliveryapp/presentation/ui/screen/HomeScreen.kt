package com.example.fooddeliveryapp.presentation.ui.screen

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
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.List
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.RestrauntMenuScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

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
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val cartViewModel: CartViewModel = koinViewModel()
    val cartCount = cartViewModel.uiState.items.size

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = homeViewModel.selectedTab,
                onItemSelected = homeViewModel::onTabSelected
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            HomeTopBar(
                cartCount = cartCount,
                showBackButton = false,
                onCartClick = {}
            )

            HomeSearchBar()
            Spacer(modifier = Modifier.height(8.dp))

            when (homeViewModel.selectedTab) {
                BottomNavItem.Home -> {
                    LazyColumn {
                        items(homeViewModel.restaurants) { restaurant ->
                            RestaurantCard(
                                restaurant = restaurant,
                                onClick = {
                                    navigator.navigate(
                                        RestrauntMenuScreenDestination(
                                            restaurantName = restaurant.name
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                BottomNavItem.Orders -> {
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
fun HomeTopBar(
    cartCount: Int,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (showBackButton) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
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

        Box {
            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart"
                )
            }

            if (cartCount > 0) {
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(cartCount.toString())
                }
            }
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







