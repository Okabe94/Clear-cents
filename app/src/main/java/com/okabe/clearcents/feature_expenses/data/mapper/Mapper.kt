package com.okabe.clearcents.feature_expenses.data.mapper

interface Mapper<Entity, Domain> {
    fun toDomain(entity: Entity): Domain
    fun toData(domain: Domain): Entity
}