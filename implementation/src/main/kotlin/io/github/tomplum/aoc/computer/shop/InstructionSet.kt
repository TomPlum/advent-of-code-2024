package io.github.tomplum.aoc.computer.shop

interface InstructionSet {
    fun apply(memory: String): Int
}