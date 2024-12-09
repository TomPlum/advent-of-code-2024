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

    fun compressFiles(): FileSystem {
        val fs = FileSystem(data)

        fs.files.keys.sortedDescending().forEach { fileId ->
            val candidateFileIndices = fs.files[fileId]!!
            val fileSize = candidateFileIndices.last - candidateFileIndices.first + 1

            val suitableEmptyBlock = fs.getContiguousEmptyBlocks().find { emptyBlockIndices ->
                val emptyBlockSize = emptyBlockIndices.last - emptyBlockIndices.first + 1
                fileSize <= emptyBlockSize && emptyBlockIndices.last < candidateFileIndices.first
            }

            if (suitableEmptyBlock != null) {
                candidateFileIndices.forEach { fileIndex ->
                    fs.clearBlock(fileIndex)
                }

                (suitableEmptyBlock.first..<suitableEmptyBlock.first + fileSize).forEach { emptyBlockIndex ->
                    fs.setBlockValue(emptyBlockIndex, fileId)
                }
            }
        }

        return fs
    }
}