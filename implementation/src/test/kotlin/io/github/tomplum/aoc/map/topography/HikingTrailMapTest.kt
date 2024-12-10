package io.github.tomplum.aoc.map.topography

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class HikingTrailMapTest {
    @Test
    fun partOneExampleLarger() {
        val input = TestInputReader.read<String>("/day10/example-larger.txt")
        val hikingTrailMap = HikingTrailMap(input.value)
        assertThat(hikingTrailMap.findTrailheads()).isEqualTo(36)
    }
}