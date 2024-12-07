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
        val operations = mutableListOf(Operation.ADD, Operation.MULTIPLY)
        val totalCalibrationResult = ropeBridgeSchematics.getTotalCalibrationResult(operations)
        assertThat(totalCalibrationResult).isEqualTo(3749L)
    }

    @Test
    fun partTwoExample() {
        val input = TestInputReader.read<String>("/day7/example.txt")
        val ropeBridgeSchematics = RopeBridgeSchematics(input.value)
        val operations = Operation.entries
        val totalCalibrationResult = ropeBridgeSchematics.getTotalCalibrationResult(operations)
        assertThat(totalCalibrationResult).isEqualTo(11387L)
    }
}