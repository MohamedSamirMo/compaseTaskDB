package com.task.compasetask.data.models

import androidx.annotation.DrawableRes
import com.task.compasetask.R

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    @DrawableRes val imageRes: Int,   // الآن يستخدم صور حقيقية من drawable
    val shortDescription: String = "",
    val description: String = "",
    val category: String = "",
    val calories: String = "0 cal"
)

val sampleProducts = listOf(
    // Featured Donuts
    Product(
        id = 1,
        name = "Pumpkin Spice",
        price = 4.00,
        imageRes = R.drawable.ic_dounts_1,     // ضع الصورة في res/drawable/pumpkin_spice.png
        shortDescription = "Glazed old fashioned pumpkin spice cake donut",
        description = "Our famous glazed old-fashioned pumpkin spice cake donut, made with real pumpkin and warm fall spices. Topped with a sweet maple glaze and a hint of cinnamon. Perfect with your morning coffee.",
        category = "donut",
        calories = "410 cal"
    ),
    Product(
        id = 2,
        name = "Strawberry Lemon",
        price = 3.75,
        imageRes = R.drawable.ic_dounts_1,
        shortDescription = "Tangy strawberry lemonade flavored donut with lemon glaze",
        description = "A refreshing burst of summer! Tangy strawberry lemonade flavored donut with a zesty lemon glaze and real strawberry bits. Light, fruity and utterly delicious.",
        category = "donut",
        calories = "380 cal"
    ),
    Product(
        id = 3,
        name = "Glazed Donut",
        price = 2.99,
        imageRes = R.drawable.ic_dounts_1,
        shortDescription = "Classic yeast donut with sweet glaze",
        description = "The classic you love. Our original yeast-raised donut dipped in a sweet, glossy glaze that melts in your mouth. Simple, timeless, and always satisfying.",
        category = "donut",
        calories = "300 cal"
    ),
    Product(
        id = 4,
        name = "Chocolate Sprinkle",
        price = 3.49,
        imageRes = R.drawable.ic_dounts_1,
        shortDescription = "Chocolate frosted donut topped with rainbow sprinkles",
        description = "Rich chocolate-frosted donut covered in colorful rainbow sprinkles. A fun and festive treat that brings joy with every bite.",
        category = "donut",
        calories = "350 cal"
    ),
    Product(
        id = 5,
        name = "Strawberry Frosted",
        price = 3.29,
        imageRes = R.drawable.ic_dounts_1,
        shortDescription = "Strawberry icing on a soft cake donut",
        description = "A soft cake donut topped with luscious strawberry frosting and a drizzle of pink glaze. Sweet, fruity and irresistible.",
        category = "donut",
        calories = "320 cal"
    ),
    Product(
        id = 6,
        name = "Boston Cream",
        price = 3.99,
        imageRes = R.drawable.ic_dounts_1,
        shortDescription = "Filled with vanilla custard and topped with chocolate glaze",
        description = "An indulgent classic: soft donut filled with smooth vanilla custard, then dipped in rich chocolate glaze. Decadent and creamy.",
        category = "donut",
        calories = "420 cal"
    ),
    // Hot Drinks
    Product(
        id = 7,
        name = "Original Coffee",
        price = 2.50,
        imageRes = R.drawable.ic_caffee,
        shortDescription = "Classic medium-roast brewed coffee",
        description = "Our signature medium-roast coffee, freshly brewed from 100% Arabica beans. Smooth, balanced, and aromatic. The perfect pick-me-up.",
        category = "hot drink",
        calories = "5 cal"
    ),
    Product(
        id = 8,
        name = "Tea",
        price = 2.00,
        imageRes = R.drawable.ic_caffee,
        shortDescription = "Hot tea served with your choice of black, green, or herbal",
        description = "Choose from black, green, or our herbal infusions. Each cup is steeped to perfection for a soothing and aromatic tea experience.",
        category = "hot drink",
        calories = "5 cal"
    ),
    Product(
        id = 9,
        name = "Espresso",
        price = 2.75,
        imageRes = R.drawable.ic_caffee,
        shortDescription = "Single shot of rich espresso",
        description = "A single shot of intense, full-bodied espresso with a rich crema. Bold and energizing, crafted from premium Italian roast.",
        category = "hot drink",
        calories = "5 cal"
    ),
    // Iced Drinks
    Product(
        id = 10,
        name = "Iced Caramel Latte",
        price = 4.50,
        imageRes = R.drawable.ic_latte,
        shortDescription = "Chilled latte with sweet caramel syrup and milk",
        description = "Chilled espresso combined with creamy milk and sweet caramel syrup, served over ice. Topped with whipped cream and a caramel drizzle.",
        category = "iced drink",
        calories = "250 cal"
    ),
    Product(
        id = 11,
        name = "Iced Mocha",
        price = 4.75,
        imageRes = R.drawable.ic_latte,
        shortDescription = "Iced chocolate coffee with whipped cream",
        description = "Refreshing iced coffee blended with rich chocolate syrup and milk, finished with a swirl of whipped cream. A cool, chocolatey delight.",
        category = "iced drink",
        calories = "320 cal"
    )
)

// Helper functions
fun getProductsByCategory(category: String): List<Product> =
    sampleProducts.filter { it.category == category }

val featuredProducts = sampleProducts.take(2)

val hotDrinks = getProductsByCategory("hot drink")
val icedDrinks = getProductsByCategory("iced drink")
val donuts = getProductsByCategory("donut")

fun getProductById(id: Int): Product? = sampleProducts.find { it.id == id }