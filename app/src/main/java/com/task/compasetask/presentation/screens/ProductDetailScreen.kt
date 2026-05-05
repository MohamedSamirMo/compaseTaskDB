package com.task.compasetask.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.compasetask.data.models.getProductById
import com.task.compasetask.presentation.viewModel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val product = getProductById(productId)
    var quantity by remember { mutableStateOf(1) }

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Product not found")
        }
        return
    }

    Scaffold(
        containerColor = Color(0xFFFFF6F2),
        topBar = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Image(
                                painter = painterResource(id = com.task.compasetask.R.drawable.ic_back),
                                contentDescription = "Back",
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFFFF6F2)
                    )
                )
            }
        }
    ) { paddingValues ->
        // داخل Scaffold، استبدل الـ Column الخارجي بهذا الهيكل:
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. صورة المنتج (أيقونة) في الأعلى، خارج الكارد الأبيض
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp) // اختياري: لجعل الصورة متوسطة وليست لاصقة بالحواف (يمكنك إزالتها لو حابب تكون ممتدة)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFFFF6F2)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 0.dp) ,
                shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),   // زوايا دائرية من كل الجوانب
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)   // مسافة داخلية للمحتوى (نصوص، أزرار، إلخ)
                ) {
                    // باقي المحتوى كما هو دون تغيير:
                    // Row: أيقونة المفضلة + اسم المنتج
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var isFavorite by remember { mutableStateOf(false) }
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = CircleShape,
                            color = Color(0xFF174C4F),
                            onClick = { isFavorite = !isFavorite }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Text(
                            text = product.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF174C4F)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                        // الوصف القصير + السعرات + زر إظهار التفاصيل
                        var showDescription by remember { mutableStateOf(false) }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "${product.shortDescription} • ${product.calories}",
                                fontSize = 15.sp,
                                color = Color(0xFF174C4F),
                                lineHeight = 20.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                TextButton(
                                    onClick = { showDescription = !showDescription },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(
                                            0xFF174C4F
                                        )
                                    ),
                                    modifier = Modifier.padding(start = 0.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text ="Allergen & Ingredient",
                                            fontSize = 12.sp,
                                            color = Color(0xFF174C4F)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "❮",
                                            fontSize = 18.sp,
                                            modifier = Modifier.rotate(if (showDescription) -90f else 90f),
                                            color = Color(0xFF174C4F)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        if (showDescription) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState()),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                elevation = CardDefaults.cardElevation(1.dp)
                            ) {
                                Text(
                                    text = product.description,
                                    fontSize = 12.sp,
                                    color = Color(0xFF174C4F),
                                    lineHeight = 18.sp,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                        // نهاية المحتوى الداخلي للكارد الأبيض
                    }
                }
            }
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                shadowElevation = 8.dp,
                color = Color(0xFFFFF6F2)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { quantity++ },
                                modifier = Modifier.size(25.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFFFFFFFF),
                                    contentColor = Color(0xFF174C4F)
                                ),
                            ) {
                                Image(
                                    painter = painterResource(id = com.task.compasetask.R.drawable.ic_add),
                                    contentDescription = "add",
                                )
                            }
                            Text(
                                text = quantity.toString(),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF174C4F),
                                modifier = Modifier
                                    .width(30.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier.size(25.dp),
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFFFFFFFF),
                                    contentColor = Color(0xFF174C4F)
                                ),
                            ) {
                                Image(
                                    painter = painterResource(id = com.task.compasetask.R.drawable.ic_minu),
                                    contentDescription = "minus",
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Price",
                                fontSize = 12.sp,
                                color = Color(0xFF174C4F),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "$${String.format("%.2f", product.price)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF174C4F)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            repeat(quantity) {
                                cartViewModel.addToCart(product)
                            }
                            quantity = 1
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF8C42),
                            contentColor = Color(0xFFFFF6F2)
                        ),
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) {
                        Text(
                            text = "Add to bag",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}