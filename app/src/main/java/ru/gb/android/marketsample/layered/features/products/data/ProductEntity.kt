package ru.gb.android.marketsample.layered.features.products.data

import kotlinx.serialization.Serializable

@Serializable
class ProductEntity (
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
)