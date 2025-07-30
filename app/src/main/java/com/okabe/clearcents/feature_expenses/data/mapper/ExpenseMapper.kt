package com.okabe.clearcents.feature_expenses.data.mapper

import com.okabe.clearcents.feature_expenses.data.entity.ExpenseEntity
import com.okabe.clearcents.feature_expenses.domain.mapper.ListMapper
import com.okabe.clearcents.feature_expenses.domain.model.ExpenseModel

class ExpenseMapper : ListMapper<ExpenseEntity, ExpenseModel> {

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

    override fun toDomain(entity: Collection<ExpenseEntity>): List<ExpenseModel> =
        entity.map { toDomain(it) }

    override fun toData(domain: Collection<ExpenseModel>): List<ExpenseEntity> =
        domain.map { toData(it) }

}