package io.github.tomplum.aoc.disk

import io.github.tomplum.libs.extensions.product

data class FileSystem(val data: String) {

    private val blockSpaces = data.map { it.digitToInt() }.sum()
    private val emptyBlockIndices = mutableListOf<Int>()
    private val blocks = IntArray(blockSpaces) { -1 }

    val lastIndex = blockSpaces - 1

    init {
        var diskMapIndex = 0
        var fsIndex = 0

        var id = 0

        while (diskMapIndex <= data.length - 1) {
            val blockSize = data[diskMapIndex].digitToInt()
            val blockRange = fsIndex..<fsIndex + blockSize
            blockRange.forEach { targetIndex ->
                blocks[targetIndex] = id
            }

            if (diskMapIndex < data.length - 1) {
                val emptySpace = data[diskMapIndex + 1].digitToInt()
                val emptySpaceRange = blockRange.last + 1..blockRange.last + emptySpace
                emptySpaceRange.forEach { targetIndex ->
                    emptyBlockIndices.add(targetIndex)
                    blocks[targetIndex] = -1
                }

                fsIndex += blockSize + emptySpace
            }

            id++
            diskMapIndex += 2
        }
    }

    fun setBlockValue(index: Int, value: Int) {
        blocks[index] = value
    }

    fun getBlockValue(index: Int) = blocks[index]

    fun isEmptyBlock(index: Int): Boolean {
        return blocks[index] == -1
    }

    fun clearBlock(index: Int) {
        blocks[index] = -1
    }

    fun clone(): FileSystem {
        return FileSystem(data)
    }

    fun getFirstEmptyBlockIndex(): Int {
        return emptyBlockIndices.removeFirst()
    }

    fun calculateChecksum(): Int = blocks.mapIndexed { blockIndex, blockValue ->
        if (blockValue != -1) {
            blockIndex * blockValue
        } else {
            0
        }
    }.sum()

    fun hasConsolidatedBlocks(): Boolean {
        return blocks.copyOfRange(lastIndex - emptyBlockIndices.size + 1, lastIndex).all { it == -1 }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileSystem

        return blocks.contentEquals(other.blocks)
    }

    override fun hashCode(): Int {
        return blocks.contentHashCode()
    }

    override fun toString(): String {
        return blocks.joinToString(" ") { block ->
            if (block == -1) {
                "."
            } else {
                block.toString()
            }
        }
    }
}