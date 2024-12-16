package io.github.tomplum.aoc.map.maze

import io.github.tomplum.libs.algorithm.Node
import io.github.tomplum.libs.algorithm.dijkstraShortestPath
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.*
import kotlin.collections.ArrayDeque

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
//        return search(listOf(Triple(startingPosition, startingDirection, 0)))
//        return dfs(startingPosition to startingDirection)
      /*  return dijkstraShortestPath(
            startingPositions = listOf(Triple(startingPosition, startingDirection, 0)),
            evaluateAdjacency = { (currentNode) ->
                val (pos, direction, score) = currentNode

                // If we keep moving in the current direction
                val nextPos = Node(Triple(pos.shift2(direction), direction, score + 1), score + 1)

                // If we decide to rotate in the current position
                val rotations = directions
                    .filterNot { it == direction || it.isOpposite(direction) }
                    .filterNot { getTile(pos.shift2(it), MazeTile('?')).isWall() }
                    .map { Node(Triple(pos, it, score + 1000), score + 1000) }

                (rotations + nextPos).filter { getTile(it.value.first, MazeTile('?')).isTraversable() }
            },
            processNode = { _, adjacentNode ->
                adjacentNode
            },
            terminates = { (currentNode) -> getTile(currentNode.first, MazeTile('?')).isEnd() }
        )*/

        return customDijkstra2(startingPosition to startingDirection)
    }

    private fun customDijkstra(startingPosition: Pair<Point2D, Direction>): Int {
        // A map of nodes and the shortest distance from the given starting positions to it
        val distance = mutableMapOf<Pair<Point2D, Direction>, Int>()

        // Unsettled nodes that are yet to be evaluated. Prioritised by their distance from the start
        val next = PriorityQueue<Node<Pair<Point2D, Direction>>>()

        // Settled nodes whose shortest path has been calculated and need not be evaluated again
        val settled = mutableSetOf<Pair<Point2D, Direction>>()

        next.offer(Node(startingPosition, 0))

        while(next.isNotEmpty()) {
            // Take the next node from the queue, ready for evaluation
            val currentNode = next.poll()

            // Consider the current node settled now
            settled.add(currentNode.value)

            // If the terminal condition has been met
            // (I.e. the current node is the location we want to find the shortest path to)
            // then we stop and return the current known shortest distance to it
            if (getTile(currentNode.value.first, MazeTile('?')).isEnd()) {
                return currentNode.distance
            }

            // Find all the adjacent nodes to the current one (as per the given predicate)
            // and evaluate each one
            val (pos, direction) = currentNode.value

            // If we keep moving in the current direction
            val nextPos = Node(pos.shift2(direction) to direction, currentNode.distance + 1)

            // If we decide to rotate in the current position
            val rotations = directions
                .filterNot { it == direction || it.isOpposite(direction) }
                .filterNot { getTile(pos.shift2(it), MazeTile('?')).isWall() }
                .map { Node(pos to it, currentNode.distance + 1000) }

            val adjacent = (rotations + nextPos).filter { getTile(it.value.first, MazeTile('?')).isTraversable() }
            adjacent.forEach { adjacentNode ->
                // Perform any additional processing on the adjacent node before evaluation
                val evaluationNode = adjacentNode //processNode(currentNode, adjacentNode)

                if (!settled.contains(evaluationNode.value)) {
                    // The new shortest path to the adjacent node is the current nodes distance
                    // plus the weight of the node being evaluated
                    val updatedDistance = currentNode.distance + evaluationNode.distance

                    // If the distance of this new path is shorter than the shortest path we're
                    // already aware of, then we can update it since we've found a shorter one
                    if (updatedDistance < distance.getOrDefault(evaluationNode.value, Int.MAX_VALUE)) {
                        // Track the new shortest path
                        distance[evaluationNode.value] = updatedDistance

                        // Queue up the adjacent node to continue down that path
                        next.add(Node(evaluationNode.value, updatedDistance))
                    }
                }
            }
        }

        val message = "Could not find a path from the given starting positions to the node indicated by the terminates predicate."
        throw IllegalStateException(message)
    }

    private fun customDijkstra2(startingPosition: Pair<Point2D, Direction>): Int {
        // A map of nodes and the shortest distance from the given starting position to them
        val distances = mutableMapOf<Pair<Point2D, Direction>, Int>().withDefault { Int.MAX_VALUE }

        // Priority queue of nodes to evaluate, prioritized by their distance from the start
        val next = PriorityQueue<Node<Pair<Point2D, Direction>>>(compareBy { it.distance })

        // Set of visited nodes
        val settled = mutableSetOf<Pair<Point2D, Direction>>()

        // Initialize the queue with the starting position
        distances[startingPosition] = 0
        next.offer(Node(startingPosition, 0))

        while (next.isNotEmpty()) {
            val currentNode = next.poll()
            val (currentPosition, currentDirection) = currentNode.value

            // Mark the current node as settled
            if (!settled.add(currentNode.value)) continue

            // Terminal condition: If we've reached the end node, return the shortest distance
            if (getTile(currentPosition, MazeTile('?')).isEnd()) {
                return currentNode.distance
            }

            // Generate adjacent nodes
            val adjacentNodes = mutableListOf<Node<Pair<Point2D, Direction>>>()

            // 1. Move in the current direction (if possible)
            val nextPosition = currentPosition.shift2(currentDirection)
            if (getTile(nextPosition, MazeTile('?')).isTraversable()) {
                adjacentNodes.add(Node(nextPosition to currentDirection, currentNode.distance + 1))
            }

            // 2. Rotate in place (left and right, excluding opposite direction)
            val rotations = directions
                .filterNot { it == currentDirection || it.isOpposite(currentDirection) }
                .map { Node(currentPosition to it, currentNode.distance + 1000) }
            adjacentNodes.addAll(rotations)

            // Evaluate each adjacent node
            for (adjacent in adjacentNodes) {
                val (adjPosition, adjDirection) = adjacent.value

                if (!settled.contains(adjacent.value)) {
                    val newDistance = currentNode.distance + (if (adjPosition == currentPosition) 1000 else 1)

                    // If the new distance is shorter, update and add to the queue
                    if (newDistance < distances.getValue(adjacent.value)) {
                        distances[adjacent.value] = newDistance
                        next.offer(Node(adjacent.value, newDistance))
                    }
                }
            }
        }

        throw IllegalStateException("Could not find a path to the target node.")
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

    private fun dfs(start: Pair<Point2D, Direction>): Int {
        val visited = mutableSetOf<Triple<Point2D, Direction, Int>>()
        val stack = ArrayDeque<Triple<Point2D, Direction, Int>>()
        stack.add(Triple(start.first, start.second, 0))

        while(stack.isNotEmpty()) {
            val current = stack.removeLast()
            val (pos, direction, score) = current

            if (current !in visited) {
                visited.add(current)

                // If we keep moving in the current direction
                val nextPos = Triple(pos.shift2(direction), direction, score + 1)

                // If we decide to rotate in the current position
                val rotations = directions
                    .filterNot { it == direction || it.isOpposite(direction) }
                    .filterNot { getTile(pos.shift2(it), MazeTile('?')).isWall() }
                    .map { Triple(pos, it, score + 1000) }


                val candidates = (rotations + nextPos)
                    .filter { getTile(it.first, MazeTile('?')).isTraversable() }

                stack.addAll(candidates.reversed())
            }
        }

        return visited.minOf { it.third }
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