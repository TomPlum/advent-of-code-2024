package io.github.tomplum.aoc.report

import assertk.assertThat
import assertk.assertions.hasSize
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ReportAnalyserTest {
    @Test
    fun examplePartOne() {
        val reportData = TestInputReader.read<String>("/day2/example-1.txt").value
        val reportAnalyser = ReportAnalyser(reportData)
        val safeReports = reportAnalyser.getSafeReports()
        assertThat(safeReports).hasSize(2)
    }

    @Test
    fun examplePartTwo() {
        val reportData = TestInputReader.read<String>("/day2/example-1.txt").value
        val reportAnalyser = ReportAnalyser(reportData)
        val safeReports = reportAnalyser.getSafeReportsWithProblemDampener()
        assertThat(safeReports).hasSize(4)
    }
}