package com.task.compasetask.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.task.compasetask.data.models.CartItem
import com.task.compasetask.data.models.Order
import com.task.compasetask.data.models.Product
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFFF9666) else Color.Transparent,
            contentColor = if (isSelected) Color.White else Color(0xFFFF9666)
        ),
        shape = RoundedCornerShape(32.dp),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(1.dp) else ButtonDefaults.buttonElevation(0.dp),

    ) {
        Text(text, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}
@Composable
fun ProductCard(
    product: Product,
    onAddToCart: () -> Unit,
    onClick: () -> Unit,
    isFeatured: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(if (isFeatured) 20.dp else 20.dp),
        elevation = CardDefaults.cardElevation(if (isFeatured) 8.dp else 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isFeatured) Color(0xFFFFF3E0) else Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(if (isFeatured) 16.dp else 16.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.name,
                modifier = Modifier.size(if (isFeatured) 150.dp else 150.dp)
            )
            Spacer(modifier = Modifier.height(if (isFeatured) 12.dp else 12.dp))

            Text(
                product.name,
                fontWeight = FontWeight.SemiBold,
                color= Color(0xFF174C4F),
                fontSize = if (isFeatured) 18.sp else 18.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                "$${product.price}",
                color =  Color(0xFF174C4F),
                fontSize = if (isFeatured) 16.sp else 16.sp,
                fontWeight =  FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}
@Composable
fun SimpleProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(product.imageRes),
            contentDescription = product.name,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            product.name,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF174C4F),
            fontSize = 14.sp
        )
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.product.name,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF174C4F),
                    fontSize = 16.sp
                )
                Text(
                    text = "$${String.format("%.2f", item.product.price)}",
                    color = Color(0xFF795548),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onDecrease,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Decrease",
                        tint = Color(0xFF795548)
                    )
                }
                Text(
                    text = item.quantity.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF174C4F)
                )
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = Color(0xFF795548)
                    )
                }
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remove",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }
        }
    }
}

@Composable
fun OrderItemCard(
    order: Order,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(order.timestamp))

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Order #${order.id.take(8)}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF174C4F),
                    fontSize = 16.sp
                )
                Text(
                    text = formattedDate,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                order.items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.product.name} × ${item.quantity}",
                            color = Color(0xFF4A3728),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "$${String.format("%.2f", item.product.price * item.quantity)}",
                            color = Color(0xFF795548),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Divider(thickness = 0.5.dp, color = Color(0xFFFFF0E6))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF174C4F)
                )
                Text(
                    text = "$${String.format("%.2f", order.totalPrice)}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF795548)
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFE8F5E9),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Completed",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}