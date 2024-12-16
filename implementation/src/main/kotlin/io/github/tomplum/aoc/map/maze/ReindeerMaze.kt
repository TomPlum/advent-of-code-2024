package io.github.tomplum.aoc.map.maze

import io.github.tomplum.libs.algorithm.Node
import io.github.tomplum.libs.algorithm.dijkstraAllShortestPaths
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

                val nextPosition = currentPosition.shift(direction)
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
            terminates = { currentNode ->
                getTile(currentNode.value.first, MazeTile('?')).isEnd()
            }
        )
    }

    fun countBestPathTiles(): Int {
        val (startingPosition) = findTile { it.isReindeerStart() }!!
        val startingDirection = Direction.RIGHT

        return dijkstraAllShortestPaths(
            startingPositions = listOf(startingPosition to startingDirection),
            evaluateAdjacency = { currentNode ->
                val (currentPosition, direction) = currentNode.value
                val adjacentNodes = mutableListOf<Node<Pair<Point2D, Direction>>>()

                val nextPosition = currentPosition.shift(direction)
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
            terminates = { currentNode ->
                getTile(currentNode.value.first, MazeTile('?')).isEnd()
            }
        ).shortestPaths.flatten().map { (pos) -> pos }.distinct().count()
    }
}