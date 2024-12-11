package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.stones.PlutonianPebbles
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day11: Solution<Long, Long> {
    private val input = InputReader.read<String>(Day(11)).asSingleString()
    private val plutonian = PlutonianPebbles(input)

    override fun part1(): Long {
        return plutonian.blink(25)
    }

    override fun part2(): Long {
        return plutonian.blink(75)
    }
}