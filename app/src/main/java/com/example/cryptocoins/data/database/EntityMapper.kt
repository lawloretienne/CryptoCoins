package com.example.cryptocoins.data.database

/**
 * Every class, that can be mapped to entity model should implement this interface
 */
interface EntityMapper<R> {
    fun toEntityModel(): R
}

fun <R, T : EntityMapper<R>> Iterable<T>.toEntityModels(): List<R> {
    return this.map { it.toEntityModel() }
}