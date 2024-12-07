package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.bridge.Operation
import io.github.tomplum.aoc.bridge.RopeBridgeSchematics
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day7: Solution<Long, Long> {
    private val input = InputReader.read<String>(Day(7)).value
    private val ropeBridgeSchematics = RopeBridgeSchematics(input)

    override fun part1(): Long {
        val operations = mutableListOf(Operation.ADD, Operation.MULTIPLY)
        return ropeBridgeSchematics.getTotalCalibrationResult(operations)
    }

    override fun part2(): Long {
        val operations = Operation.entries
        return ropeBridgeSchematics.getTotalCalibrationResult(operations)
    }
}