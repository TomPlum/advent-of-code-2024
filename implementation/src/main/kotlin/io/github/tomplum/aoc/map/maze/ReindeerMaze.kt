package io.github.tomplum.aoc.map.maze

import io.github.tomplum.libs.algorithm.Node
import io.github.tomplum.libs.algorithm.dijkstraShortestPath
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.*

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

    fun countBestPathTiles(): Int {
        val (startingPosition) = findTile { it.isReindeerStart() }!!
        val startingDirection = Direction.RIGHT

        return dijkstraAllShortestPaths(
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
            uniqueKeySelector = { currentNode -> currentNode.first },
            terminates = { currentNode ->
                getTile(currentNode.value.first, MazeTile('?')).isEnd()
            }
        ).second
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

    private fun <N, U> dijkstraAllShortestPaths(
        startingPositions: Collection<N>,
        evaluateAdjacency: (currentNode: Node<N>) -> Collection<Node<N>>,
        terminates: (currentNode: Node<N>) -> Boolean,
        uniqueKeySelector: (node: N) -> U,
        processNode: (currentNode: Node<N>, adjacentNode: Node<N>) -> Node<N> = { _, adjacentNode -> adjacentNode }
    ): Pair<Int, Int> {
        // A map of nodes and the shortest distance from the starting positions to it
        val distance = mutableMapOf<N, Int>()

        // A map to track predecessors for each node (to reconstruct paths)
        val predecessors = mutableMapOf<N, MutableSet<N>>()

        // Unsettled nodes prioritized by their distance
        val next = PriorityQueue<Node<N>>()

        startingPositions.forEach { startingPosition ->
            next.offer(Node(startingPosition, 0))
            distance[startingPosition] = 0
        }

        var shortestDistance: Int? = null

        while (next.isNotEmpty()) {
            // Take the next node from the queue
            val currentNode = next.poll()

            // If we have determined the shortest distance and this node's distance exceeds it, stop processing
            if (shortestDistance != null && currentNode.distance > shortestDistance) break

            // If the termination condition is met, record the shortest distance
            if (terminates(currentNode)) {
                if (shortestDistance == null) {
                    shortestDistance = currentNode.distance
                }
            }

            // Find all adjacent nodes
            evaluateAdjacency(currentNode).forEach { adjacentNode ->
                // Perform additional processing on the adjacent node before evaluation
                val evaluationNode = processNode(currentNode, adjacentNode)

                // The new shortest path to the adjacent node
                val updatedDistance = currentNode.distance + evaluationNode.distance

                // If the distance of this new path is shorter than or equal to the known shortest distance
                if (updatedDistance <= distance.getOrDefault(evaluationNode.value, Int.MAX_VALUE)) {
                    // Update distance
                    distance[evaluationNode.value] = updatedDistance

                    // Update predecessors
                    predecessors.computeIfAbsent(evaluationNode.value) { mutableSetOf() }.add(currentNode.value)

                    // Add the adjacent node to the queue
                    next.add(Node(evaluationNode.value, updatedDistance))
                }
            }
        }

        // Now we need to collect all nodes that are part of the shortest paths
        val shortestPathNodes = mutableSetOf<N>()

        // Helper function to collect all nodes along the shortest paths
        fun collectShortestPathNodes(node: N) {
            if (shortestPathNodes.contains(node)) return // Already visited
            shortestPathNodes.add(node)

            predecessors[node]?.forEach { parent ->
                collectShortestPathNodes(parent)
            }
        }

        // Collect all nodes for the shortest path, starting from the end nodes
        // This step ensures we get all nodes in all shortest paths, not just the first one
        val endNodes = distance.filter { terminates(Node(it.key, it.value)) }.keys
        endNodes.forEach { collectShortestPathNodes(it) }

        // Apply the uniqueness criterion to count unique nodes
        val uniqueSet = shortestPathNodes.map(uniqueKeySelector).toSet()

        return (shortestDistance ?: 0) to uniqueSet.size
    }
}