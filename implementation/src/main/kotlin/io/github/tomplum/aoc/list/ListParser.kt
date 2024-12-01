package io.github.tomplum.aoc.list

import io.github.tomplum.libs.logging.AdventLogger
import kotlin.math.abs

class ListParser(private val list: List<String>) {

    /**
     * Parses a collection of Strings (Usually puzzle input lines)
     * vertically to produce two lists.
     *
     * For example:
     *  1  5
     *  8  2
     *  3  7
     *
     * Would produce two lists
     *  [1, 8, 3]
     *  [5, 2, 7]
     *
     * @param parse A lambda that takes a string and returns a Pair of values (T, U) if parsing is successful.
     * @return Two lists of values from the vertical representation in the string collection.
     */
    fun <T, U> Collection<String>.toVerticalLists(parse: (String) -> Pair<T, U>?): Pair<MutableList<T>, MutableList<U>> {
        val first = mutableListOf<T>()
        val second = mutableListOf<U>()

        for (string in this) {
            parse(string)?.let { (a, b) ->
                first.add(a)
                second.add(b)
            }
        }

        return Pair(first, second)
    }

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
            AdventLogger.info(similarityScore)
        }

        return similarityScore
    }
}