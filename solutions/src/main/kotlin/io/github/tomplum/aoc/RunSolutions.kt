package io.github.tomplum.aoc

import io.github.tomplum.aoc.solutions.*
import io.github.tomplum.libs.solutions.SolutionRunner
import java.time.Year

fun main() {
    SolutionRunner.run(
        Year.of(2023),
        Day1(), Day2(), Day3(), Day4(), Day5(), Day6(), Day7(), Day8(), Day9(), Day10(),
        Day11(), Day12(), Day13(), /*Day14(),*/ Day15(), Day16()
    )
}
