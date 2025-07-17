package com.okabe.clearcents.feature_expenses.data.mapper

import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel

class ExpenseMapper : Mapper<ExpenseEntity, ExpenseModel> {

    override fun toDomain(entity: ExpenseEntity) = ExpenseModel(
        expenseId = entity.expenseId,
        amount = entity.amount,
        categoryIdForeign = entity.categoryIdForeign,
        date = entity.date,
        description = entity.description
    )

    override fun toData(domain: ExpenseModel) = ExpenseEntity(
        expenseId = domain.expenseId,
        amount = domain.amount,
        categoryIdForeign = domain.categoryIdForeign,
        date = domain.date,
        description = domain.description
    )
}