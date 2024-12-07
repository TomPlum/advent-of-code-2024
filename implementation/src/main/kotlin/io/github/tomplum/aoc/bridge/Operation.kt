package io.github.tomplum.aoc.bridge

enum class Operation {
    ADD, MULTIPLY;

    fun apply(first: Long, second: Long) = when (this) {
        ADD -> first + second
        MULTIPLY -> first * second
    }
}