package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.computer.Computer
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day3: Solution<Int, Int> {
    private val computer = Computer()
    private val memory = InputReader.read<String>(Day(3)).asSingleString()

    override fun part1(): Int {
        return computer.runProgram(memory)
    }
}