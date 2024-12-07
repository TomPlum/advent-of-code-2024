package io.github.tomplum.aoc.list

import io.github.tomplum.libs.extensions.toVerticalLists
import kotlin.math.abs

class ListParser(private val list: List<String>) {

    fun calculateDistance(): Int {
        val (firstList, secondList) = list.toVerticalLists { line -> line.split("  ").map { it.trim().toInt() }.let { Pair(it[0], it[1] ) } }

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
        val (firstList, secondList) = list.toVerticalLists { line -> line.split("  ").map { it.trim().toInt() }.let { Pair(it[0], it[1] ) } }

        var similarityScore = 0

        firstList.forEach { first ->
            val secondListCount = secondList.count { it == first }
            similarityScore += first * secondListCount
        }

        return similarityScore
    }
}