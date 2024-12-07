package io.github.tomplum.aoc.bridge

data class CalibrationEquation(private val equation: String) {
    val testValue = equation.split(":")[0].trim().toLong()

    private val values = equation.split(":")[1].trim().split(" ").map { it.trim().toLong() }

    fun hasValidOperatorConfig(operations: List<Operation>): Boolean = findResultPermutations(values.toMutableList(), operations)
        .any { result -> result == testValue }

    private fun findResultPermutations(numbers: MutableList<Long>, operations: List<Operation>): List<Long> {
        if (numbers.size == 1) {
            return listOf(numbers.first())
        }

        val first = numbers.removeFirst()
        val second = numbers.removeFirst()

        return operations.flatMap { operation ->
            val workingValue = operation.apply(first, second)
            val newNumbers = mutableListOf(workingValue) + numbers
            findResultPermutations(newNumbers.toMutableList(), operations)
        }
    }
}