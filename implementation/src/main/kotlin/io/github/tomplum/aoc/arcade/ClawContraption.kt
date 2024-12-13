package io.github.tomplum.aoc.arcade

import io.github.tomplum.libs.extensions.split

/**
 * TODO: Move to math package in libs
 */
fun solveLinearEquations(a1: Double, b1: Double, c1: Double, a2: Double, b2: Double, c2: Double): Pair<Double, Double>? {
    val determinant = a1 * b2 - a2 * b1

    if (determinant == 0.0) {
        return null
    }

    val determinantX = c1 * b2 - c2 * b1
    val determinantY = a1 * c2 - a2 * c1

    val x = determinantX / determinant
    val y = determinantY / determinant

    return x to y
}

class ClawContraption(list: List<String>) {
    private val machines = list
        .split { it.isBlank() }
        .map { ClawMachine(it.toList()) }

    fun simulate(offset: Long = 0): Long = machines.sumOf { machine ->
        val (ax, ay) = machine.aButtonBehaviour
        val (bx, by) = machine.bButtonBehaviour
        val (px, py) = machine.prizeLocation.let { (x, y) -> x + offset to y + offset }
        val presses  = solveLinearEquations(
            ax.toDouble(), bx.toDouble(), px.toDouble(),
            ay.toDouble(), by.toDouble(), py.toDouble()
        )

        if (presses != null && presses.first % 1 == 0.0 && presses.second % 1 == 0.0) {
            presses.first.toLong() * 3 + presses.second.toLong()
        } else {
            0
        }
    }
}