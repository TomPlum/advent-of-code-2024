package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.submarine.WarehouseSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day15: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(15)).value
    private val warehouseSimulator = WarehouseSimulator(input)

    override fun part1(): Int {
        return warehouseSimulator.simulate()
    }
}