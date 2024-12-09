package io.github.tomplum.aoc.disk

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class DiskMapTest {
    @Test
    fun partOneExample() {
        val input = TestInputReader.read<String>("/day9/example.txt").asSingleString()
        val diskMap = DiskMap(input)
        assertThat(diskMap.getFileSystemChecksum()).isEqualTo(1928)
    }
}