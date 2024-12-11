package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day11Test {
    @Test
    fun part1() {
        assertThat(Day11().part1()).isEqualTo(217443)
    }

    @Test
    fun part2() {
        assertThat(Day11().part2()).isEqualTo(257246536026785)
    }
}
