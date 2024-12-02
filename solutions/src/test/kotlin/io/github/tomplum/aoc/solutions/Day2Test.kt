package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day2Test {
    @Test
    fun partOne() {
        val solution = Day2().part1()
        assertThat(solution).isEqualTo(442)
    }
}