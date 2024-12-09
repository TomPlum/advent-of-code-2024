package io.github.tomplum.aoc.disk

data class FileSystem(val data: String) {

    /**
     * The number of blocks available in the file system.
     * Is determined by the file and empty blocks in [data].
     */
    private val blockSpaces = data.map { it.digitToInt() }.sum()

    /**
     * Indexes of blocks within the file system that
     * are empty. These are represented by blocks
     * whose values are -1.
     */
    private val emptyBlockIndices = mutableListOf<Int>()

    /**
     * A map of file id to the indices of the blocks
     * of that files data.
     */
    val files = mutableMapOf<Int, IntRange>()

    /**
     * Blocks within the file system that can store 1-bit
     * of data each. All blocks are initialised as empty (-1).
     */
    private val blocks = IntArray(blockSpaces) { -1 }

    /**
     * The index of the last block in the file system.
     */
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
                files[id] = blockRange
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

    fun calculateChecksum(): Long = blocks.mapIndexed { blockIndex, blockValue ->
        if (blockValue != -1) {
            blockIndex * blockValue.toLong()
        } else {
            0L
        }
    }.sum()

    fun hasConsolidatedBlocks(): Boolean {
        return blocks.copyOfRange(lastIndex - emptyBlockIndices.size + 1, lastIndex).all { it == -1 }
    }

    fun getContiguousEmptyBlocks(): List<IntRange> {
        var currentBlockStart = -1
        var insideEmptyBlock = false
        val contiguousEmptyBlocks = mutableListOf<IntRange>()

        blocks.forEachIndexed { i, value ->
            if (insideEmptyBlock && value != -1) {
                contiguousEmptyBlocks.add(currentBlockStart..<i)
                insideEmptyBlock = false
            }

            if (!insideEmptyBlock && value == -1) {
                insideEmptyBlock = true
                currentBlockStart = i
            }
        }

        return contiguousEmptyBlocks
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