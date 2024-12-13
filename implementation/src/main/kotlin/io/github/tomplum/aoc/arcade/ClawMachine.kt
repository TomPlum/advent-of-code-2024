package io.github.tomplum.aoc.arcade

import io.github.tomplum.libs.math.point.Point2D

data class ClawMachine(private val data: List<String>) {
    val aButtonBehaviour = data.first()
        .removePrefix("Button A: ")
        .split(", ")
        .extractPoint("X+", "Y+")

    val bButtonBehaviour = data[1]
        .removePrefix("Button B: ")
        .split(", ")
        .extractPoint("X+", "Y+")

    val prizeLocation = data.last()
        .removePrefix("Prize: ")
        .split(", ")
        .extractPoint("X=", "Y=")

    private fun List<String>.extractPoint(prefixOne: String, prefixTwo: String): Point2D {
        return Point2D(this[0].removePrefix(prefixOne).toInt(), this[1].removePrefix(prefixTwo).toInt())
    }
}