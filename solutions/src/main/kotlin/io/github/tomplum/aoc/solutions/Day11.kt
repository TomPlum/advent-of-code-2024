package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.stones.PlutonianPebbles
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day11: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(11)).asSingleString()
    private val plutonian = PlutonianPebbles(input)

    override fun part1(): Int {
        return plutonian.blink(25)
    }
}