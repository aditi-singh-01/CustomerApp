package com.example.fooddeliveryapp.presentation.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fooddeliveryapp.presentation.ui.viewModel.CartViewModel
import com.example.fooddeliveryapp.presentation.ui.viewModel.RestaurantMenuViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import com.example.fooddeliveryapp.presentation.ui.model.MenuItemUiModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination
fun RestrauntMenuScreen(
   restaurantName: String,
   navigator: DestinationsNavigator,
   viewModel: RestaurantMenuViewModel = koinViewModel(),
   cartViewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
   val state = viewModel.uiState

   Scaffold(
      bottomBar = {
         BottomNavBar(
            selectedItem = state.selectedTab,
            onItemSelected = viewModel::onTabSelected
         )
      }
   ) {
      Column (){
         HomeTopBar(
            cartCount = cartViewModel.cartItemCount,
            showBackButton = true,
            onBackClick = {
               navigator.popBackStack()
            }
         )

         Spacer(modifier = Modifier.height(8.dp))

         Text(
            text = restaurantName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
         )

         Spacer(modifier = Modifier.height(8.dp))

         LazyColumn {
            items(state.menuItems) { item ->
               MenuItemCard(
                  item = item,
                  onAddClick = { cartViewModel.addItem() }
               )
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
      modifier = Modifier
         .fillMaxWidth()
         .padding(horizontal = 16.dp, vertical = 8.dp)
         .clickable { onClick() },
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
            Text(restaurant.name, style = MaterialTheme.typography.titleMedium)
            Text("⭐ ${restaurant.rating}")
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
         .padding(16.dp),
      shape = RoundedCornerShape(12.dp),
      elevation = CardDefaults.cardElevation(3.dp)
   ) {
      Row(modifier = Modifier.padding(12.dp)) {

         AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
         )

         Spacer(modifier = Modifier.width(12.dp))

         Column(modifier = Modifier.weight(1f)) {
            Text(item.name, style = MaterialTheme.typography.titleMedium)
            Text(item.description, style = MaterialTheme.typography.bodySmall)
            Text("₹${item.price}", color = MaterialTheme.colorScheme.primary)
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
