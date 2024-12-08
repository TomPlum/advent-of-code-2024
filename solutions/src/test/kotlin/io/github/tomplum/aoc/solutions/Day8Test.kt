package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day8Test {
    @Test
    fun part1() {
        assertThat(Day8().part1()).isEqualTo(265)
    }

    @Test
    fun part2() {
        assertThat(Day8().part2()).isEqualTo(962)
    }
}