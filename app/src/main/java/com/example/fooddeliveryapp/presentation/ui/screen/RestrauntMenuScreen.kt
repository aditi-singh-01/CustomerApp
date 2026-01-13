package com.example.fooddeliveryapp.presentation.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fooddeliveryapp.data.model.local.CartItem
import com.example.fooddeliveryapp.presentation.ui.model.MenuItemUiModel
import com.example.fooddeliveryapp.presentation.ui.screen.destinations.CartScreenDestination
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartIntent
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel.SideEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Destination
@Composable
fun RestrauntMenuScreen(
   restaurantName: String,
   menuUrl: String,
   navigator: DestinationsNavigator,
   viewModel: RestaurantMenuViewModel = koinViewModel(),
   cartViewModel: CartViewModel = koinViewModel()
) {
   val state by viewModel.collectAsState()
   val cartState by cartViewModel.state.collectAsState()

   val snackbarHostState = remember { SnackbarHostState() }

   LaunchedEffect(menuUrl) {
      viewModel.onIntent(
         RestaurantMenuViewModel.MenuIntent.LoadMenu(menuUrl)
      )
   }


   viewModel.collectSideEffect { effect ->
      when (effect) {
         is SideEffect.ShowError -> {
            snackbarHostState.showSnackbar(effect.message)
         }
      }
   }

   Scaffold(
      topBar = {
         HomeTopBar(
            cartCount = cartState.items.size,
            showBackButton = true,
            onBackClick = { navigator.popBackStack() },
            onCartClick = {
               navigator.navigate(CartScreenDestination)
            }
         )
      },
      snackbarHost = {
         SnackbarHost(snackbarHostState)
      }
   ) { paddingValues ->

      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
      ) {

         Text(
            text = restaurantName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
         )

         when {
            state.isLoading -> {
               Box(
                  modifier = Modifier.fillMaxSize(),
                  contentAlignment = Alignment.Center
               ) {
                  CircularProgressIndicator()
               }
            }

            state.menuItems.isEmpty() -> {
               Box(
                  modifier = Modifier.fillMaxSize(),
                  contentAlignment = Alignment.Center
               ) {
                  Text("No items available")
               }
            }

            else -> {
               LazyColumn(
                  contentPadding = PaddingValues(bottom = 16.dp)
               ) {
                  items(state.menuItems, key = { it.id }) { item ->
                     MenuItemCard(
                        item = item,
                        onAddClick = {
                           cartViewModel.onIntent(
                              CartIntent.AddItem(
                                 CartItem(
                                    id = item.id,
                                    name = item.name,
                                    price = item.price,
                                    imageUrl = item.imageUrl
                                 )
                              )
                           )
                        }
                     )
                  }
               }
            }
         }
      }
   }
}

@Composable
fun MenuItemCard(
   item: MenuItemUiModel,
   onAddClick: () -> Unit
) {
   Card(
      modifier = Modifier
         .fillMaxWidth()
         .padding(horizontal = 16.dp, vertical = 8.dp),
      shape = RoundedCornerShape(12.dp),
      elevation = CardDefaults.cardElevation(3.dp)
   ) {
      Row(
         modifier = Modifier.padding(12.dp),
         verticalAlignment = Alignment.CenterVertically
      ) {

         AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            modifier = Modifier
               .size(72.dp)
               .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
         )

         Spacer(modifier = Modifier.width(12.dp))

         Column(modifier = Modifier.weight(1f)) {
            Text(
               text = item.name,
               style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
               text = item.description,
               style = MaterialTheme.typography.bodySmall,
               color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
               text = "₹${item.price}",
               style = MaterialTheme.typography.bodyMedium,
               color = MaterialTheme.colorScheme.primary
            )
         }

         Button(
            onClick = onAddClick,
            shape = RoundedCornerShape(8.dp)
         ) {
            Text("Add")
         }
      }
   }
}