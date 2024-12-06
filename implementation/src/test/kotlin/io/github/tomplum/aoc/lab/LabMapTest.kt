package io.github.tomplum.aoc.lab

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class LabMapTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day6/example.txt")
        val labMap = LabMap(input.value)
        assertThat(labMap.followGuardRoute()).isEqualTo(41)
    }
}