package com.example.cryptocoins.domain

/**
 * Every class, that can be mapped to domain model should implement this interface
 */
interface DomainMapper<R> {
    fun toDomainModel(): R
}

fun <R, T : DomainMapper<R>> Iterable<T>.toDomainModels(): List<R> {
    return this.map { it.toDomainModel() }
}