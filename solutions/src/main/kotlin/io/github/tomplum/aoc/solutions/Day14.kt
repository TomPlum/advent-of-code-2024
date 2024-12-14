package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.robot.BathroomSecuritySimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day14: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(14)).value
    private val bathroomSecuritySimulator = BathroomSecuritySimulator(input)

    override fun part1(): Int {
        return bathroomSecuritySimulator.simulate(seconds = 100, width = 101, height = 103)
    }
}