package com.sparshchadha.expensetracker.feature.expense.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ExpenseEntity(
    @PrimaryKey
    val cardId: String = UUID.randomUUID().toString(),
    val userOwnerId: String,
    val cardLimit: Int,
    val currentSpending: Int,
    val cardName: String,
    val isPrimary: Boolean,
)
