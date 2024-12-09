package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.disk.DiskMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day9: Solution<Long, Long> {
    private val input = InputReader.read<String>(Day(9)).asSingleString()
    private val diskMap = DiskMap(input)

    override fun part1(): Long {
        return diskMap.getFileSystemChecksum()
    }

    override fun part2(): Long {
        return diskMap.compressFiles().calculateChecksum()
    }
}