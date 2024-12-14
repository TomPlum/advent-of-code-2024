package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day14Test {
    @Test
    fun part1() {
        assertThat(Day14().part1()).isEqualTo(221616000)
    }
}