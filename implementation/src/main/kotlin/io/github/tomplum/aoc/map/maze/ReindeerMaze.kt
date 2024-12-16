package io.github.tomplum.aoc.map.maze

import io.github.tomplum.libs.algorithm.Node
import io.github.tomplum.libs.algorithm.dijkstraShortestPath
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

        return dijkstraShortestPath(
            startingPositions = listOf(startingPosition to startingDirection),
            evaluateAdjacency = { currentNode ->
                val (currentPosition, direction) = currentNode.value
                val adjacentNodes = mutableListOf<Node<Pair<Point2D, Direction>>>()

                val nextPosition = currentPosition.shift2(direction)
                if (getTile(nextPosition, MazeTile('?')).isTraversable()) {
                    adjacentNodes.add(Node(nextPosition to direction, 1))
                }

                val rotations = directions
                    .filterNot { currentDirection ->
                        val isSameDirection = currentDirection == direction
                        val isBacktracking = currentDirection.isOpposite(direction)
                        isSameDirection || isBacktracking
                    }
                    .map { currentDirection ->
                        Node(currentPosition to currentDirection, 1000)
                    }

                adjacentNodes.addAll(rotations)

                adjacentNodes
            },
            processNode = { _, adjacent ->  adjacent },
            terminates = { currentNode ->
                getTile(currentNode.value.first, MazeTile('?')).isEnd()
            }
        )
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