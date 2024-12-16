package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.maze.ReindeerMaze
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day16: Solution<Int, Int> {
    private val input = InputReader.read<String>(Day(16)).value
    private val reindeerMaze = ReindeerMaze(input)

    override fun part1(): Int {
        return reindeerMaze.calculateLowestPossibleScore()
    }
}