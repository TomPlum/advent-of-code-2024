package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.arcade.ClawContraption
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day13: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(13)).value
    private val clawContraption = ClawContraption(input)

    override fun part1(): Int {
        return clawContraption.simulate()
    }
}