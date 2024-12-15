package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day15Test {
    @Test
    fun part1() {
        assertThat(Day15().part1()).isEqualTo(1487337)
    }
}