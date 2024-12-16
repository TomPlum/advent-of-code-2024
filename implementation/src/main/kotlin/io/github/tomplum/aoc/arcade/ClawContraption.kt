package io.github.tomplum.aoc.arcade

import io.github.tomplum.libs.extensions.split
import io.github.tomplum.libs.math.equation.LinearEquation


class ClawContraption(list: List<String>) {
    private val machines = list
        .split { it.isBlank() }
        .map { ClawMachine(it.toList()) }

    fun simulate(offset: Long = 0): Long = machines.sumOf { machine ->
        val (ax, ay) = machine.aButtonBehaviour
        val (bx, by) = machine.bButtonBehaviour
        val (px, py) = machine.prizeLocation.let { (x, y) -> x + offset to y + offset }
        val presses  = LinearEquation(
            ax.toDouble(), bx.toDouble(), px.toDouble(),
            ay.toDouble(), by.toDouble(), py.toDouble()
        ).solve()

        if (presses != null && presses.first % 1 == 0.0 && presses.second % 1 == 0.0) {
            presses.first.toLong() * 3 + presses.second.toLong()
        } else {
            0
        }
    }
}