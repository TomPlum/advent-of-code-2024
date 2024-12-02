package io.github.tomplum.aoc.report

import kotlin.math.abs

class ReportAnalyser(private val data: List<String>) {
    fun getSafeReports(): List<String> = data.filter { isReportSafe(it) }

    fun getSafeReportsWithProblemDampener(): List<String> {
        return data.filter { line ->
            val values = line.split(" ").map { it.trim().toInt() }

            val singleLevelRemoved = values.indices.map { i ->
                val originalReportValues = values.toMutableList()
                originalReportValues.removeAt(i)
                originalReportValues
            }.map { it.joinToString(" ") }

            val reportPermutations = singleLevelRemoved + values.joinToString(" ")

            reportPermutations.any { isReportSafe(it) }
        }
    }

    private fun isReportSafe(report: String): Boolean {
        val values = report.split(" ").map { it.trim().toInt() }
        val sorted = values.sorted()
        val sortedDesc = values.sortedDescending()

        var hasAdjacencySafety = true

        values.windowed(2) { (a, b) ->
            val delta = abs(a - b)
            if (delta < 1 || delta > 3) {
                hasAdjacencySafety = false
            }
        }

        return (sorted == values || sortedDesc == values) && hasAdjacencySafety
    }
}