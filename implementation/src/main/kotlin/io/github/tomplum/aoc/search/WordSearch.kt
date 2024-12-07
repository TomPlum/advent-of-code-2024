package io.github.tomplum.aoc.search

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class WordSearch(data: List<String>): AdventMap2D<WordSearchTile>() {
    init {
        init(data) { value ->
            WordSearchTile(value as Char)
        }
    }

    fun getXmasOccurrences(): Int {
        val startingTiles = filterTiles { tile -> tile.value == 'X' }
        return startingTiles.keys.sumOf { position ->
            findXmasWord(position)
        }
    }

    fun getMasXOccurrences(): Int {
        val startingTiles = filterTiles { tile -> tile.value == 'A' }
        return startingTiles.keys.count { position ->
            isValidCrossCenter(position)
        }
    }

    private fun findXmasWord(position: Point2D, currentWord: String = "X", matches: Int = 0, direction: Direction? = null): Int {
        if (currentWord == "XMAS") {
            return matches + 1
        }

        val targetValue = when(currentWord) {
            "X" -> 'M'
            "XM" -> 'A'
            "XMA" -> 'S'
            else -> throw IllegalArgumentException("The current word $currentWord is not valid.")
        }

        return adjacentTiles(setOf(position), WordSearchTile('.'))
            .filter { (candidatePos, tile) ->
                // If we have no direction then we must
                // be at X looking for M, so we don't care about
                // the direction as there could be many MAS instances
                // from the current starting X value.
                if (direction == null) {
                    tile?.value == targetValue
                } else {
                    // If we have a direction then we're at least at XM
                    // so we've already started looking into a direction
                    // and must continue until we finish XMAS, or it fails.
                    val candidateDirection = position.directionTo(candidatePos as Point2D).first!!
                    tile?.value == targetValue && candidateDirection == direction
                }
            }
            .keys.sumOf { newPosition ->
                val targetDirection: Direction = direction ?: position.directionTo(newPosition as Point2D).first!!
                findXmasWord(newPosition as Point2D, "$currentWord$targetValue", matches, targetDirection)
            }
    }

    private fun isValidCrossCenter(position: Point2D): Boolean {
        val candidates = position.diagonallyAdjacent()
        val topRight = position.shift(Direction.TOP_RIGHT)
        val bottomRight = position.shift(Direction.BOTTOM_RIGHT)
        val bottomLeft = position.shift(Direction.BOTTOM_LEFT)
        val topLeft = position.shift(Direction.TOP_LEFT)

        val sValid = candidates.count { getTile(it, WordSearchTile('.')).value == 'S' } == 2
        val mValid = candidates.count { getTile(it, WordSearchTile('.')).value == 'M' } == 2

        if (!sValid || !mValid) {
            return false
        }

        val bottomTopWordInvalid = getTile(topRight, WordSearchTile('.')).value == getTile(bottomLeft, WordSearchTile('.')).value
        val topBottomWordInvalid = getTile(topLeft, WordSearchTile('.')).value == getTile(bottomRight, WordSearchTile('.')).value

        return !(bottomTopWordInvalid || topBottomWordInvalid)
    }
}