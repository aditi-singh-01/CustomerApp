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

data class MenuItemUiModel(
   val name: String,
   val description: String,
   val price: Int,
   val imageUrl: String,
   val rating: Double
)

val dummyMenuItems = listOf(
   MenuItemUiModel(
      name = "Chicken Biryani",
      description = "Aromatic basmati rice cooked with tender chicken and spices",
      price = 220,
      imageUrl = "https://images.unsplash.com/photo-1633945274309-2c16c9682c5b?w=800&h=800&fit=crop&auto=format",
      rating = 4.6
   ),
   MenuItemUiModel(
      name = "Paneer Butter Masala",
      description = "Soft paneer cubes in rich tomato butter gravy",
      price = 180,
      imageUrl = "https://images.unsplash.com/photo-1628294896516-344152572ee3?w=800&h=800&fit=crop&auto=format",
      rating = 4.5
   ),
   MenuItemUiModel(
      name = "Masala Dosa",
      description = "Crispy dosa with spiced potato filling",
      price = 120,
      imageUrl = "https://images.unsplash.com/photo-1626078299003-9a3a6b69c8b3?w=800&h=800&fit=crop&auto=format",
      rating = 4.4
   ),
   MenuItemUiModel(
      name = "Butter Chicken",
      description = "Creamy tomato-based curry with grilled chicken",
      price = 240,
      imageUrl = "https://images.unsplash.com/photo-1600628422019-7f44e40f4f90?w=800&h=800&fit=crop&auto=format",
      rating = 4.7
   ),
   MenuItemUiModel(
      name = "Veg Thali",
      description = "Assorted Indian curries served with rice and roti",
      price = 200,
      imageUrl = "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=800&h=800&fit=crop&auto=format",
      rating = 4.3
   ),
   MenuItemUiModel(
      name = "Gobi Manchurian",
      description = "Crispy cauliflower tossed in spicy sauce",
      price = 150,
      imageUrl = "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=800&h=800&fit=crop&auto=format",
      rating = 4.2
   )
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Destination
fun RestrauntMenuScreen(
   restaurantName: String,
   navigator: DestinationsNavigator,
   viewModel: RestaurantMenuViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
   cartViewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
   Scaffold(
      bottomBar = {
         BottomNavBar(
            selectedItem = viewModel.selectedTab,
            onItemSelected = viewModel::onTabSelected
         )
      }
   ) {
      Column {

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
            items(viewModel.menuItems) { item ->
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
