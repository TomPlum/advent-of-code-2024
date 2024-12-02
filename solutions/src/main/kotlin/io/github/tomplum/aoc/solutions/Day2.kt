package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.report.ReportAnalyser
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day2 : Solution<Int, Int> {
    private val reportData = InputReader.read<String>(Day(2)).value
    private val reportAnalyser = ReportAnalyser(reportData)

    override fun part1(): Int {
        return reportAnalyser.getSafeReports().count()
    }

    override fun part2(): Int {
        return reportAnalyser.getSafeReportsWithProblemDampener().count()
    }
}