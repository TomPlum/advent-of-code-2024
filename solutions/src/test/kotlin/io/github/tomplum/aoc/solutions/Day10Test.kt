package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun part1() {
        assertThat(Day10().part1()).isEqualTo(798)
    }
}