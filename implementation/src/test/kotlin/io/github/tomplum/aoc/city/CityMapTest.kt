package io.github.tomplum.aoc.city

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CityMapTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day8/example.txt")
        val cityMap = CityMap(input.value)
        val model = ResonantFrequencyModel()
        assertThat(cityMap.getAntiNodes(model)).isEqualTo(14)
    }

    @Test
    fun partTwoExample() {
        val input = TestInputReader.read<String>("/day8/example.txt")
        val cityMap = CityMap(input.value)
        val model = ResonantHarmonicsModel()
        assertThat(cityMap.getAntiNodes(model)).isEqualTo(34)
    }
}