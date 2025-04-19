package com.example.sodv3203_final_project.Data.Orders

data class CartItem(
    val menuItemId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val customizations: List<OrderCustomization> = emptyList(),
    val instructions: String = "",
    val finalPrice: Double = price
)
