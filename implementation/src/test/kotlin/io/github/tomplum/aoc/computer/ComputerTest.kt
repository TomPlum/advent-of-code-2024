package io.github.tomplum.aoc.computer

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ComputerTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("/day3/example.txt")
        val scanner = Computer(BasicInstructionSet())
        val result = scanner.runProgram(input.asSingleString())
        assertThat(result).isEqualTo(161)
    }

    @Test
    fun examplePartTwo() {
        val input = TestInputReader.read<String>("/day3/example-2.txt")
        val scanner = Computer(ConditionalInstructionSet())
        val result = scanner.runProgram(input.asSingleString())
        assertThat(result).isEqualTo(48)
    }
}