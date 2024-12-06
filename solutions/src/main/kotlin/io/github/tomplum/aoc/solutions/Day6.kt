package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.lab.LabMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day6: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(6)).value
    private val labMap = LabMap(input)

    override fun part1(): Int {
        return labMap.simulateGuardPatrol().size
    }
}