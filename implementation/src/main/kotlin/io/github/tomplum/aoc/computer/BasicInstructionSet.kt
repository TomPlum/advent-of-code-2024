package io.github.tomplum.aoc.computer

class BasicInstructionSet: InstructionSet {
    override fun apply(memory: String): Int {
        return Regex("""mul\(\d{1,3},\d{1,3}\)""").findAll(memory).sumOf { result ->
            MultiplyInstruction(result.value).run()
        }
    }
}