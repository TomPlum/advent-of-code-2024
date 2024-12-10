package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.topography.HikingTrailMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day10: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(10)).value
    private val hikingTrailMap = HikingTrailMap(input)

    override fun part1(): Int {
        return hikingTrailMap.getTrailHeadScoreSum()
    }

    override fun part2(): Int {
        return hikingTrailMap.getTrailHeadRatingSum()
    }
}