package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day7Test {
    private val solution = Day7()

    @Test
    fun part1() {
        assertThat(solution.part1()).isEqualTo(2664460013123L)
    }

    @Test
    fun part2() {
        assertThat(solution.part2()).isEqualTo(426214131924213L)
    }
}