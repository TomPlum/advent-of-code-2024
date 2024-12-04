package io.github.tomplum.aoc.search

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class WordSearchTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day4/example.txt")
        val wordSearch = WordSearch(input.value)
        assertThat(wordSearch.getXmasOccurrences()).isEqualTo(18)
    }

    @Test
    fun partTwoExample() {
        val input = TestInputReader.read<String>("/day4/example.txt")
        val wordSearch = WordSearch(input.value)
        assertThat(wordSearch.getMasXOccurrences()).isEqualTo(9)
    }
}