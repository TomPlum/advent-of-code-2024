package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day6Test {
    private val solution = Day6()

    @Test
    fun part1() {
        val answer = solution.part1()
        assertThat(answer).isEqualTo(5067)
    }

    @Test
    fun part2() {
        val answer = solution.part2()
        assertThat(answer).isEqualTo(1793)
    }
}