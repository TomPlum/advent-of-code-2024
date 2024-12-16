package io.github.tomplum.aoc.map.maze

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ReindeerMazeTest {
    @Test
    fun partOneExampleOne() {
        val input = TestInputReader.read<String>("/day16/example-1.txt")
        val reindeerMaze = ReindeerMaze(input.value)
        assertThat(reindeerMaze.calculateLowestPossibleScore()).isEqualTo(7036)
    }

    @Test
    fun partOneExampleTwo() {
        val input = TestInputReader.read<String>("/day16/example-2.txt")
        val reindeerMaze = ReindeerMaze(input.value)
        assertThat(reindeerMaze.calculateLowestPossibleScore()).isEqualTo(11048)
    }

    @Test
    fun partTwoExampleOne() {
        val input = TestInputReader.read<String>("/day16/example-1.txt")
        val reindeerMaze = ReindeerMaze(input.value)
        assertThat(reindeerMaze.countBestPathTiles()).isEqualTo(45)
    }

    @Test
    fun partTwoExampleTwo() {
        val input = TestInputReader.read<String>("/day16/example-2.txt")
        val reindeerMaze = ReindeerMaze(input.value)
        assertThat(reindeerMaze.countBestPathTiles()).isEqualTo(64)
    }
}