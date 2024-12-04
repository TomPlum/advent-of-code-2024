package io.github.tomplum.aoc.search

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

class WordSearch(data: List<String>): AdventMap2D<WordSearchTile>() {
    init {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                val tile = WordSearchTile(column)
                val position = Point2D(x, y)
                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun getXmasOccurrences(): Int {
        val startingTiles = filterTiles { tile -> tile.value == 'X' }
        return startingTiles.keys.sumOf { position ->
            findXmasWord(position)
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
                if (direction == null) {
                    tile?.value == targetValue
                } else {
                    val candidateDirection = position.directionTo(candidatePos as Point2D).first!!
                    tile?.value == targetValue && candidateDirection == direction
                }
            }
            .keys.sumOf { newPosition ->
                val targetDirection: Direction = direction ?: position.directionTo(newPosition as Point2D).first!!
                findXmasWord(newPosition as Point2D, "$currentWord$targetValue", matches, targetDirection)
            }
    }

    /**
     * TODO: Consume from lib once published.
     */
    fun Point2D.directionTo(other: Point2D): Pair<Direction?, Point2D> {
        val xDelta = other.x - x
        val yDelta = other.y - y
        val distance = Point2D(abs(xDelta), abs(yDelta))

        return when {
            yDelta > 0 && xDelta == 0 -> Direction.UP
            yDelta < 0 && xDelta == 0 -> Direction.DOWN
            xDelta > 0 && yDelta == 0 -> Direction.RIGHT
            xDelta < 0 && yDelta == 0 -> Direction.LEFT
            yDelta < 0 && xDelta > 0 -> Direction.BOTTOM_RIGHT
            yDelta > 0 && xDelta < 0 -> Direction.TOP_LEFT
            yDelta < 0 -> Direction.BOTTOM_LEFT
            yDelta > 0 -> Direction.TOP_RIGHT
            else -> null
        }.let { direction ->
            Pair(direction, distance)
        }
    }
}