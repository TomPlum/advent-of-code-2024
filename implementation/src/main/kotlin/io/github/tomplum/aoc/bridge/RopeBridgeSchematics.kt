package io.github.tomplum.aoc.bridge

class RopeBridgeSchematics(input: List<String>) {

    private val equations = input.map { line -> CalibrationEquation(line)}

    fun getTotalCalibrationResult(): Long = equations
        .filter { equation -> equation.hasValidOperatorConfig() }
        .sumOf { equation -> equation.testValue
    }
}