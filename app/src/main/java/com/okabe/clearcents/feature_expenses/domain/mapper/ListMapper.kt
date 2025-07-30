package com.okabe.clearcents.feature_expenses.domain.mapper

interface ListMapper<Entity, Domain> {
    fun toDomain(entity: Entity): Domain
    fun toData(domain: Domain): Entity
    fun toDomain(entity: Collection<Entity>): List<Domain>
    fun toData(domain: Collection<Domain>): List<Entity>
}