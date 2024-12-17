package io.github.tomplum.aoc.computer

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.computer.shop.BasicInstructionSet
import io.github.tomplum.aoc.computer.shop.ConditionalInstructionSet
import io.github.tomplum.aoc.computer.shop.RentalShopComputer
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class RentalShopComputerTest {
    @Test
    fun examplePartOne() {
        val input = TestInputReader.read<String>("/day3/example.txt")
        val scanner = RentalShopComputer(BasicInstructionSet())
        val result = scanner.runProgram(input.asSingleString())
        assertThat(result).isEqualTo(161)
    }

    @Test
    fun examplePartTwo() {
        val input = TestInputReader.read<String>("/day3/example-2.txt")
        val scanner = RentalShopComputer(ConditionalInstructionSet())
        val result = scanner.runProgram(input.asSingleString())
        assertThat(result).isEqualTo(48)
    }
}