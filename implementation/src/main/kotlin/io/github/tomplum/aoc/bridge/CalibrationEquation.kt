package io.github.tomplum.aoc.bridge

data class CalibrationEquation(private val equation: String) {
    val testValue = equation.split(":")[0].trim().toInt()

    private val values = equation.split(":")[1].trim().split(" ").map { it.trim().toInt() }

    fun hasValidOperatorConfig(): Boolean = recurse(values.toMutableList())
        .any { result -> result == testValue }

    private fun recurse(numbers: MutableList<Int>): List<Int> {
        if (numbers.size == 1) {
            return listOf(numbers.first())
        }

        val first = numbers.removeFirst()
        val second = numbers.removeFirst()

        return Operation.entries.flatMap { operation ->
            val workingValue = operation.apply(first, second)
            val newNumbers = mutableListOf(workingValue) + numbers
            recurse(newNumbers.toMutableList())
        }
    }
}