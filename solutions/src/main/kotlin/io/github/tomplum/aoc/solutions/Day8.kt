package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.city.CityMap
import io.github.tomplum.aoc.city.model.ResonantFrequencyModel
import io.github.tomplum.aoc.city.model.ResonantHarmonicsModel
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day8: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(8)).value
    private val cityMap = CityMap(input)

    override fun part1(): Int {
        val model = ResonantFrequencyModel()
        return cityMap.getAntiNodes(model)
    }

    override fun part2(): Int {
        val model = ResonantHarmonicsModel()
        return cityMap.getAntiNodes(model)
    }
}