package com.example.cryptocoins.data.network

/**
 * Every class, that can be mapped to response model should implement this interface
 */
interface ResponseMapper<R> {
    fun toResponseModel(): R
}

fun <R, T : ResponseMapper<R>> Iterable<T>.toResponseModels(): List<R> {
    return this.map { it.toResponseModel() }
}