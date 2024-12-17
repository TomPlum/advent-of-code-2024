package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.computer.shop.BasicInstructionSet
import io.github.tomplum.aoc.computer.shop.RentalShopComputer
import io.github.tomplum.aoc.computer.shop.ConditionalInstructionSet
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day3: Solution<Int, Int> {
    private val memory = InputReader.read<String>(Day(3)).asSingleString()

    override fun part1(): Int {
        val rentalShopComputer = RentalShopComputer(BasicInstructionSet())
        return rentalShopComputer.runProgram(memory)
    }

    override fun part2(): Int {
        val rentalShopComputer = RentalShopComputer(ConditionalInstructionSet())
        return rentalShopComputer.runProgram(memory)
    }
}