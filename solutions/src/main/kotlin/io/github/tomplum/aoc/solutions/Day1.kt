package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.list.ListParser
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day1 : Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(1)).value

    private val listParser = ListParser(input)

    override fun part1(): Int {
        return listParser.calculateDistance()
    }

    override fun part2(): Int {
        return listParser.calculateSimilarityScore()
    }
}
