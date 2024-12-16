package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun part1() {
        assertThat(Day16().part1()).isEqualTo(65436)
    }
}