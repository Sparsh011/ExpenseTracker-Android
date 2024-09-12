package com.sparshchadha.expensetracker.feature.expense_cards.domain.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sparshchadha.expensetracker.feature.profile.data.remote.dto.UserProfile
import java.util.UUID

@Entity
data class ExpenseCardEntity(
    @PrimaryKey
    val cardId: String = UUID.randomUUID().toString(),
    val userOwnerId: String,
    val cardLimit: Int,
    val currentSpending: Int,
    val cardName: String,
    val isPrimary: Boolean,
)
