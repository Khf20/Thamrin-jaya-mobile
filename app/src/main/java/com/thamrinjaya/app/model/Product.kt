package com.thamrinjaya.app.model

import androidx.annotation.DrawableRes

data class QualityPillar(
    val number: String,
    val title: String,
    val description: String
)

data class ProductionStep(
    val stepNumber: String,
    val title: String,
    val description: String,
    val sla: String,
    val qcChecklist: List<String>
)

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val badge: String,
    val description: String,
    val material: String,
    val dimensions: String,
    val finishing: String,
    val moq: String,
    val leadTime: String,
    @DrawableRes val imageResId: Int? = null
)

