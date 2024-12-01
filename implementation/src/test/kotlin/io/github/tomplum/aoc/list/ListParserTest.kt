package io.github.tomplum.aoc.list

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ListParserTest {
    private val input = TestInputReader.read<String>("day1/example.txt")

    @Test
    fun examplePartOne() {
        val parser = ListParser(input.value)
        val distance = parser.calculateDistance()
        assertThat(distance).isEqualTo(11)
    }

    @Test
    fun examplePartTwo() {
        val parser = ListParser(input.value)
        val distance = parser.calculateSimilarityScore()
        assertThat(distance).isEqualTo(31)
    }
}