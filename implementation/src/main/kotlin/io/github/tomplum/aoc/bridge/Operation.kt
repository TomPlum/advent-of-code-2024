package io.github.tomplum.aoc.bridge

enum class Operation {
    ADD, MULTIPLY;

    fun apply(first: Int, second: Int) = when (this) {
        ADD -> first + second
        MULTIPLY -> first * second
    }
}