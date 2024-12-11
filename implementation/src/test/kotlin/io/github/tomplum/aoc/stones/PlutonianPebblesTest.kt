package io.github.tomplum.aoc.stones

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PlutonianPebblesTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day11/example.txt")
        val plutonianPebbles = PlutonianPebbles(input.asSingleString())
        assertThat(plutonianPebbles.blink(25)).isEqualTo(55312)
    }
}