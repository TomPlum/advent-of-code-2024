package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.search.WordSearch
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day4: Solution<Int, Int> {
    private val wordSearchData = InputReader.read<String>(Day(4)).value
    private val wordSearch = WordSearch(wordSearchData)

    override fun part1(): Int {
        return wordSearch.getXmasOccurrences()
    }
}