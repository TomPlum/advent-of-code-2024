package io.github.tomplum.aoc.computer

interface InstructionSet {
    fun apply(memory: String): Int
}