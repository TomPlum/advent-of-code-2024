package io.github.tomplum.aoc.print

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PrintQueueTest {
    @Test
    fun partOneExample() {
        val data = TestInputReader.read<String>("/day5/example.txt")
        val printQueue = PrintQueue(data.value)
        assertThat(printQueue.analyse()).isEqualTo(143)
    }
}