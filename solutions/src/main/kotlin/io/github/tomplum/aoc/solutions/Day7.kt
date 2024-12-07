package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.bridge.RopeBridgeSchematics
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day7: Solution<Long, Long> {
    private val input = InputReader.read<String>(Day(7)).value
    private val ropeBridgeSchematics = RopeBridgeSchematics(input)

    override fun part1(): Long {
        return ropeBridgeSchematics.getTotalCalibrationResult()
    }

    override fun part2(): Long {
        return ropeBridgeSchematics.getTotalCalibrationResult()
    }
}