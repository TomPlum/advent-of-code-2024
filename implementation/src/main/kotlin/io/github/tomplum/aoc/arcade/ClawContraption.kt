package io.github.tomplum.aoc.arcade

import io.github.tomplum.libs.extensions.split

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

    fun simulate(): Int = machines.sumOf { machine ->
        val a = machine.aButtonBehaviour
        val b = machine.bButtonBehaviour
        val prize = machine.prizeLocation
        val presses  = solveLinearEquations(
            a.x.toDouble(), b.x.toDouble(), prize.x.toDouble(),
            a.y.toDouble(), b.y.toDouble(), prize.y.toDouble()
        )

        if (presses != null && presses.first % 1 == 0.0 && presses.second % 1 == 0.0) {
            presses.first.toInt() * 3 + presses.second.toInt()
        } else {
            0
        }
    }
}