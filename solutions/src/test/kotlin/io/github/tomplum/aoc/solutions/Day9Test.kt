package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day9Test {
    @Test
    fun part1() {
        assertThat(Day9().part1()).isEqualTo(6401092019345L)
    }

    @Test
    fun part2() {
        assertThat(Day9().part2()).isEqualTo(6431472344710L)
    }
}