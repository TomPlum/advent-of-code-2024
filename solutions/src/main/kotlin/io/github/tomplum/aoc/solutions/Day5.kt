package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.print.PrintQueue
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day5: Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(5)).value
    private val printQueue = PrintQueue(data)

    override fun part1(): Int {
        return printQueue.analyse()
    }

    override fun part2(): Int {
        return printQueue.orderAnalyse()
    }
}