package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day2Test {
    private val solution = Day2()

    @Test
    fun partOne() {
        val safeReports = solution.part1()
        assertThat(safeReports).isEqualTo(442)
    }

    @Test
    fun partTwo() {
        val safeReports = solution.part2()
        assertThat(safeReports).isEqualTo(493)
    }
}