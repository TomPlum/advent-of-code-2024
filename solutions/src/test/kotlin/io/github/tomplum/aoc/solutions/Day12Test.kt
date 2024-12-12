package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun part1() {
        assertThat(Day12().part1()).isEqualTo(1494342)
    }
}