package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day5Test {
    private val solution = Day5()

    @Test
    fun part1() {
        assertThat(solution.part1()).isEqualTo(5955)
    }
}