package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.garden.FarmMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day12: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(12)).value
    private val farmMap = FarmMap(input)

    override fun part1(): Int {
        // 1565538 too high, 1591860 too high
        return farmMap.getTotalGardenPlotFenceCost()
    }
}