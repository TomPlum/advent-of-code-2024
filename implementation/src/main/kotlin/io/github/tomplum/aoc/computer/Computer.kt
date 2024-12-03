package io.github.tomplum.aoc.computer

class Computer {
    fun runProgram(instructions: String): Int {
        return Regex("""mul\(\d{1,3},\d{1,3}\)""").findAll(instructions).sumOf { result ->
            MultiplyInstruction(result.value).run()
        }
    }
}