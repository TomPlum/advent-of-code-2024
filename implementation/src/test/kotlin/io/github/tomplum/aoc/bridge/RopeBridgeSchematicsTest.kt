package io.github.tomplum.aoc.bridge

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class RopeBridgeSchematicsTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day7/example.txt")
        val ropeBridgeSchematics = RopeBridgeSchematics(input.value)
        assertThat(ropeBridgeSchematics.getTotalCalibrationResult()).isEqualTo(3749)
    }
}