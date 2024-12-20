package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun part1() {
        assertThat(Day13().part1()).isEqualTo(29187)
    }

    @Test
    fun part2() {
        assertThat(Day13().part2()).isEqualTo(99968222587852)
    }
}