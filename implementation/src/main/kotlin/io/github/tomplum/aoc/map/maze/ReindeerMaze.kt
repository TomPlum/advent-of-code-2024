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
        return dijkstraShortestPath(
            startingPositions = listOf(getStartingPosition()),
            evaluateAdjacency = { currentNode -> getAdjacentTileCandidates(currentNode) },
            terminates = getMazePathTerminalCondition()
        )
    }

    fun countBestPathTiles(): Int {
        return dijkstraAllShortestPaths(
            startingPositions = listOf(getStartingPosition()),
            evaluateAdjacency = { currentNode -> getAdjacentTileCandidates(currentNode) },
            terminates = getMazePathTerminalCondition()
        ).shortestPaths.flatten().map { (pos) -> pos }.distinct().count()
    }

    private fun getMazePathTerminalCondition() = { currentNode: Node<Pair<Point2D, Direction>> ->
        getTile(currentNode.value.first, MazeTile('?')).isEnd()
    }

    private fun getAdjacentTileCandidates(currentNode: Node<Pair<Point2D, Direction>>): MutableList<Node<Pair<Point2D, Direction>>> {
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

        return adjacentNodes
    }

    private fun getStartingPosition(): Pair<Point2D, Direction> {
        return findTile { it.isReindeerStart() }!!.first to Direction.RIGHT
    }
}