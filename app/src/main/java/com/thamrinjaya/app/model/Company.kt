package com.thamrinjaya.app.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Company(
    val id: String,
    val number: String,
    val name: String,
    val shortName: String,
    val category: String,
    val slogan: String,
    val description: String,
    val history: String,
    val commitment: String,
    @DrawableRes val logoResId: Int,
    @DrawableRes val heroImageResId: Int? = null,
    @DrawableRes val serviceImageResId: Int? = null,
    val accentColor: Color,
    val lightColor: Color,
    val address: String,
    val area: String,
    val phone: String,
    val whatsapp: String,
    val email: String,
    val operationalHours: String,
    val mapQuery: String,
    val totalLeadTime: String,
    val categories: List<String>,
    val pillars: List<QualityPillar>,
    val products: List<Product>,
    val steps: List<ProductionStep>
)
