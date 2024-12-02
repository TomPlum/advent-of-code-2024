package io.github.tomplum.aoc.report

import kotlin.math.abs

class ReportAnalyser(private val data: List<String>) {
    fun getSafeReports(): List<String> {
        return data.filter { line ->
            val values = line.split(" ").map { it.trim().toInt() }
            val sorted = values.sorted()
            val sortedDesc = values.sortedDescending()

            var hasAdjacencySafety = true

            values.windowed(2) { (a, b) ->
                val delta = abs(a - b)
                if (delta < 1 || delta > 3) {
                    hasAdjacencySafety = false
                }
            }

            (sorted == values || sortedDesc == values) && hasAdjacencySafety
        }
    }
}