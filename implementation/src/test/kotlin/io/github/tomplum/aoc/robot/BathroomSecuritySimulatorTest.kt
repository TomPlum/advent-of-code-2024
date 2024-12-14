package io.github.tomplum.aoc.robot

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class BathroomSecuritySimulatorTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day14/example.txt")
        val bathroomSecuritySimulator = BathroomSecuritySimulator(input.value)
        assertThat(bathroomSecuritySimulator.simulate(seconds = 100, width = 11, height = 7)).isEqualTo(12)
    }
}