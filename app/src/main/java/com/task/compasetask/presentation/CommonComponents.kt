package com.task.compasetask.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.task.compasetask.data.models.CartItem
import com.task.compasetask.data.models.Order
import com.task.compasetask.data.models.Product
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFFF9666) else Color.Transparent,
            contentColor = if (isSelected) Color.White else Color(0xFFFF9666)
        ),
        shape = RoundedCornerShape(32.dp),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(4.dp) else ButtonDefaults.buttonElevation(0.dp),

    ) {
        Text(text, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}
//fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
//    Button(
//        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSelected) Color(0xFFFF9666) else Color.Transparent,
//            contentColor = if (isSelected) Color.White else Color(0xFFFF9666)
//        ),
//        shape = RoundedCornerShape(32.dp),
//        elevation = if (isSelected) ButtonDefaults.buttonElevation(4.dp) else ButtonDefaults.buttonElevation(0.dp)
//    ) {
//        Text(text, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
//    }
//}
//fun TabButton(
//    text: String,
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    borderColor: Color = Color.White
//) {
//    Button(
//        onClick = onClick,
//        modifier = Modifier.border(
//            width = 1.dp,
//            color = borderColor,
//            shape = RoundedCornerShape(26.dp)
//        ),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = if (isSelected) Color(0xFFFF9666) else Color.Transparent,
//            contentColor = if (isSelected) Color.White else Color(0xFFFFFFFF)
//        ),
//        shape = RoundedCornerShape(26.dp),
//        elevation = if (isSelected) ButtonDefaults.buttonElevation(4.dp) else ButtonDefaults.buttonElevation(0.dp)
//    ) {
//        Text(text, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
//    }
//}
@Composable
fun ProductCard(product: Product, onAddToCart: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = product.imageVector,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color(0xFF795548)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.name, fontWeight = FontWeight.Bold)
            Text("$${product.price}", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add to Cart", fontSize = MaterialTheme.typography.labelLarge.fontSize)
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, onIncrease: () -> Unit, onDecrease: () -> Unit, onRemove: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.product.name, fontWeight = FontWeight.Bold)
                Text("$${item.product.price}")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Text("-", fontSize = MaterialTheme.typography.titleLarge.fontSize)
                }
                Text(item.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                IconButton(onClick = onIncrease) {
                    Text("+", fontSize = MaterialTheme.typography.titleLarge.fontSize)
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onRemove) {
                    Text("Remove", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(order: Order) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Order #${order.id.takeLast(6)}", fontWeight = FontWeight.Bold)
                Text(
                    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(order.timestamp)),
                    color = Color.Gray,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            order.items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${item.quantity}x ${item.product.name}")
                    Text("$${item.product.price * item.quantity}")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontWeight = FontWeight.Bold)
                Text("$${order.totalPrice}", fontWeight = FontWeight.Bold, color = Color(0xFF795548))
            }
        }
    }
}