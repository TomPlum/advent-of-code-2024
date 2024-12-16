package io.github.tomplum.aoc.map.submarine

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class WarehouseSimulatorTest {
    @Test
    fun partOneExampleSmall() {
        val input = TestInputReader.read<String>("/day15/example-small.txt")
        val simulator = WarehouseSimulator(input.value)
        assertThat(simulator.simulate()).isEqualTo(2028)
    }

    @Test
    fun partOneExampleLarge() {
        val input = TestInputReader.read<String>("/day15/example-large.txt")
        val simulator = WarehouseSimulator(input.value)
        assertThat(simulator.simulate()).isEqualTo(10092)
    }

    @Test
    fun partTwoExampleLarge() {
        val input = TestInputReader.read<String>("/day15/example-large.txt")
        val simulator = WarehouseSimulator(input.value)
        assertThat(simulator.simulate(scaled = true)).isEqualTo(9021)
    }
}