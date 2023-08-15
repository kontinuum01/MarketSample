package ru.gb.android.marketsample.start.presentation

import kotlinx.serialization.Serializable

@Serializable
data class PromoEntity (
    val id: String,
    val name: String,
    val image: String,
    val discount: Double,
    val description: String,
    val type: String,
    val products: List<String>,
)