package io.github.tomplum.aoc.bridge

class RopeBridgeSchematics(input: List<String>) {

    private val equations = input.map { line -> CalibrationEquation(line)}

    fun getTotalCalibrationResult(operations: List<Operation>): Long = equations
        .filter { equation -> equation.hasValidOperatorConfig(operations) }
        .sumOf { equation -> equation.testValue
    }
}