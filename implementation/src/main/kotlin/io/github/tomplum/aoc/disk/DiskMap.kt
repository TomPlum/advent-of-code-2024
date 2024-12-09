package io.github.tomplum.aoc.disk

class DiskMap(private val data: String) {
    fun getFileSystemChecksum(): Long {
        val fs = FileSystem(data)
        val compressedFs = fs.clone()

        var i = fs.lastIndex
        while(!compressedFs.hasConsolidatedBlocks()) {
            if (!compressedFs.isEmptyBlock(i)) {
                val leftMostEmptyBlockIndex = fs.getFirstEmptyBlockIndex()
                compressedFs.setBlockValue(leftMostEmptyBlockIndex, fs.getBlockValue(i))
                compressedFs.clearBlock(i)
            }

            i--
        }

        return compressedFs.calculateChecksum()
    }
}