package io.github.tomplum.aoc.computer

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ComputerTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("/day3/example.txt")
        val scanner = Computer()
        val result = scanner.runProgram(input.asSingleString())
        assertThat(result).isEqualTo(161)
    }
}