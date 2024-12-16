package io.github.tomplum.aoc.map.maze

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class ReindeerMaze(data: List<String>): AdventMap2D<MazeTile>() {
    private val directions = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

    init {
        init(data) {
            MazeTile(it as Char)
        }
    }

    fun calculateLowestPossibleScore(): Int {
        val (startingPosition) = findTile { it.isReindeerStart() }!!
        val startingDirection = Direction.RIGHT
        return search(listOf(Triple(startingPosition, startingDirection, 0)))
    }

    private fun search(next: List<Triple<Point2D, Direction, Int>>, seen: List<Point2D> = listOf()): Int {
        val end = next.find { getTile(it.first.shift2(it.second), MazeTile('?')).isEnd() }

        if (end != null) {
            return end.third
        }

        val candidates = next.flatMap { (pos, direction, score) ->
            // If we keep moving in the current direction
            val nextPos = Triple(pos.shift2(direction), direction, score + 1)

            // If we decide to rotate in the current position
            val rotations = directions
                .filterNot { it == direction || it.isOpposite(direction) }
                .filterNot { getTile(pos.shift2(it), MazeTile('?')).isWall() }
                .map { Triple(pos, it, score + 1000) }

            rotations + nextPos
        }.filter { getTile(it.first, MazeTile('?')).isTraversable() }
            .filterNot { it.first in seen }

        return search(candidates, seen + next.map { it.first })
    }

    /**
     * TODO: Move to Direction enum in library
     */
    private fun Direction.isOpposite(other: Direction): Boolean {
        if (this == Direction.UP) {
            return other == Direction.DOWN
        }

        if (this == Direction.RIGHT) {
            return other == Direction.LEFT
        }

        if (this == Direction.DOWN) {
            return other == Direction.UP
        }

        if (this == Direction.LEFT) {
            return other == Direction.RIGHT
        }

        return false
    }

    /**
     * TODO: Take from library once published (Change to shift and pass in isRasterSystem = true)
     */
    private fun Point2D.shift2(direction: Direction, units: Int = 1, isRasterSystem: Boolean = false): Point2D = when (direction) {
        Direction.UP -> Point2D(x, if (isRasterSystem) y - units else y + units)
        Direction.RIGHT -> Point2D(x + units, y)
        Direction.DOWN -> Point2D(x, if (isRasterSystem) y + units else y - units)
        Direction.LEFT -> Point2D(x - units, y)
        Direction.TOP_RIGHT -> Point2D(x + units, y + units)
        Direction.BOTTOM_RIGHT -> Point2D(x + units, y - units)
        Direction.BOTTOM_LEFT -> Point2D(x - units, y - units)
        Direction.TOP_LEFT -> Point2D(x - units, y + units)
    }
}