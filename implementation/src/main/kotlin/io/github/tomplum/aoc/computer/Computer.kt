package io.github.tomplum.aoc.computer

class Computer {
    fun runProgram(instructions: String): Int {
        val regex = Regex("""(?:do\(\)|don't\(\)|mul\(\d{1,3},\d{1,3}\))""")
        val matches = regex.findAll(instructions).map { it.value }.toList()

        var instructionsEnabled = true

        return matches.fold(0) { sum, match ->
            when {
                match == "do()" -> {
                    instructionsEnabled = true
                    sum
                }
                match == "don't()" -> {
                    instructionsEnabled = false
                    sum
                }
                match.startsWith("mul") -> {
                    if (instructionsEnabled) {
                        sum + MultiplyInstruction(match).run()
                    } else {
                        sum
                    }
                }
                else -> {
                    throw IllegalArgumentException("Invalid memory instruction match $match")
                }
            }
        }
    }
}