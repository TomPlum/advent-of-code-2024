package io.github.tomplum.aoc.computer.shop

class RentalShopComputer(private val instructionSet: InstructionSet) {
    fun runProgram(memory: String): Int {
        return instructionSet.apply(memory)
    }
}