package io.github.tomplum.aoc.computer

import io.github.tomplum.libs.extensions.product

class MultiplyInstruction(private val instruction: String) {
    fun run() = instruction
        .removePrefix("mul(")
        .removeSuffix(")")
        .split(",")
        .map { value -> value.toInt() }
        .product() as Int
}