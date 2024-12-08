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
        assertThat(cityMap.getAntiNodes()).isEqualTo(14)
    }
}