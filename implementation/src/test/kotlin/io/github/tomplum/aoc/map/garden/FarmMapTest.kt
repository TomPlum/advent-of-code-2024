package io.github.tomplum.aoc.map.garden

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class FarmMapTest {
    @Test
    fun partOneExampleOne() {
        val input = TestInputReader.read<String>("/day12/example-small.txt").value
        val farmMap = FarmMap(input)
        assertThat(farmMap.getTotalGardenPlotFenceCost()).isEqualTo(140)
    }

    @Test
    fun partOneExampleTwo() {
        val input = TestInputReader.read<String>("/day12/example-two.txt").value
        val farmMap = FarmMap(input)
        assertThat(farmMap.getTotalGardenPlotFenceCost()).isEqualTo(772)
    }

    @Test
    fun partOneExampleThree() {
        val input = TestInputReader.read<String>("/day12/example-large.txt").value
        val farmMap = FarmMap(input)
        assertThat(farmMap.getTotalGardenPlotFenceCost()).isEqualTo(1930)
    }
}