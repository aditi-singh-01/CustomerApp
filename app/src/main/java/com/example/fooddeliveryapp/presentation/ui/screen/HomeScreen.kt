package com.example.fooddeliveryapp.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.fooddeliveryapp.presentation.ui.model.RestaurantUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.RestrauntMenuScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = koinViewModel(),
    cartViewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val cartState by cartViewModel.state.collectAsState()

    val cartCount = cartState.cartCount

    Scaffold(
        topBar = {
            HomeTopBar(
                cartCount = cartCount,
                showBackButton = false,
                onCartClick = {
                   //Navigate to cart
                }
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItem = state.selectedTab,
                onItemSelected = { tab ->
                    viewModel.onIntent(HomeViewModel.Intent.TabSelected(tab))
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            HomeSearchBar()
            Spacer(modifier = Modifier.height(12.dp))

            when (state.selectedTab) {

                BottomNavItem.Home -> {
                    if (state.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(state.restaurants) { restaurant ->
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
    restaurant: RestaurantUiModel,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {

            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column {
                Text(
                    text = "Zwiggy",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF26A69A),                     //MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Deliver to",
                    style = MaterialTheme.typography.labelMedium,
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
        }

        Box {
            IconButton(onClick = onCartClick) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
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
        placeholder = {
            Text("Search for restaurants or dishes")
        },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )

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
            icon = { Icon(BottomNavItem.Home.icon, null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

        )

        NavigationBarItem(
            selected = selectedItem == BottomNavItem.Orders,
            onClick = { onItemSelected(BottomNavItem.Orders) },
            icon = { Icon(BottomNavItem.Orders.icon, null) },
            label = { Text("Orders") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
