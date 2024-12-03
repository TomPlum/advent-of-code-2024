package io.github.tomplum.aoc.computer

class Computer(private val instructionSet: InstructionSet) {
    fun runProgram(memory: String): Int {
        return instructionSet.apply(memory)
    }
}