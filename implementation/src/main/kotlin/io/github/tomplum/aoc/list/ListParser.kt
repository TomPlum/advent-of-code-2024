package io.github.tomplum.aoc.list

import io.github.tomplum.libs.logging.AdventLogger
import kotlin.math.abs

class ListParser(private val list: List<String>) {
    fun calculateDistance(): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        list.forEach { line ->
            val numbers = line.split("  ").map { it.trim().toInt() }
            firstList.add(numbers.first())
            secondList.add(numbers.last())
        }

        firstList.sort()
        secondList.sort()

        var distance = 0

        firstList.forEachIndexed { i, first ->
            val second = secondList[i]
            distance += abs(second - first)
        }

        return distance
    }

    fun calculateSimilarityScore(): Int {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        list.forEach { line ->
            val numbers = line.split("  ").map { it.trim().toInt() }
            firstList.add(numbers.first())
            secondList.add(numbers.last())
        }

        var similarityScore = 0

        firstList.forEachIndexed { i, first ->
            val secondListCount = secondList.count { it == first }
            similarityScore += first * secondListCount
            AdventLogger.info(similarityScore)
        }

        return similarityScore
    }
}