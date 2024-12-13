package io.github.tomplum.aoc.arcade

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ClawContraptionTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("/day13/example.txt")
        val clawContraption = ClawContraption(input.value)
        assertThat(clawContraption.simulate()).isEqualTo(480)
    }

    @Test
    fun examplePartOTwo() {
        val input = TestInputReader.read<String>("/day13/example.txt")
        val clawContraption = ClawContraption(input.value)
        assertThat(clawContraption.simulate(offset = 10000000000000)).isEqualTo(875318608908)
    }
}