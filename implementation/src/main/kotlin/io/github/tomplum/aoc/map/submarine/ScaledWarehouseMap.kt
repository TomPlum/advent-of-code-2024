package io.github.tomplum.aoc.map.submarine

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class ScaledWarehouseMap(data: List<String>): AdventMap2D<WarehouseTile>(), WarehouseMap {
    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = WarehouseTile(column)

                if (tile.isWall() || tile.isEmpty()) {
                    addTile(Point2D(x, y), tile)
                    addTile(Point2D(x + 1, y), tile)
                }

                if (tile.isObstacle()) {
                    addTile(Point2D(x, y), WarehouseTile('['))
                    addTile(Point2D(x + 1, y), WarehouseTile(']'))
                }

                if (tile.isRobot()) {
                    addTile(Point2D(x, y), tile)
                    addTile(Point2D(x + 1, y), WarehouseTile('.'))
                }

                x += 2
            }

            x = 0
            y++
        }
    }

    override fun getRobotPosition() = findTile { it.isRobot() }?.first ?: throw IllegalStateException("No robot found in warehouse map")

    override fun getWarehouseTile(position: Point2D) = getTile(position)

    override fun moveRobot(currentPosition: Point2D, direction: Direction): Point2D {
        addTile(currentPosition, WarehouseTile('.'))

        val newRobotPosition = currentPosition.shift(direction)
        addTile(newRobotPosition, WarehouseTile('@'))

        return newRobotPosition
    }

    override fun getBoxes() = filterTiles { it.isObstacle() }

    override fun moveObstacle(robotPosition: Point2D, direction: Direction): Boolean {
        var hasMovedBoxes = false
        var nextPos = robotPosition.shift(direction)
        val boxes = mutableListOf<Pair<Point2D, Point2D>>()

        while(!hasMovedBoxes) {
            val nextTile = getTile(nextPos, WarehouseTile('?'))

            if (nextTile.isEmpty()) {
                // Other obstacles in the line get shifted one
                boxes.forEach { (leftPos, rightPos) ->
                    addTile(leftPos, WarehouseTile('.'))
                    addTile(rightPos, WarehouseTile('.'))
                }

                boxes.forEach { (leftPos, rightPos) ->
                    addTile(leftPos.shift(direction), WarehouseTile('['))
                    addTile(rightPos.shift(direction), WarehouseTile(']'))
                }

                hasMovedBoxes = true
            } else if (nextTile.isWall()) {
                break
            } else if (nextTile.isBox()) {
                val box = nextPos to nextPos.shift(direction)
                val boxPositions = findBoxStacks(setOf(box), direction)
                boxes.addAll(boxPositions.windowed(2) { (left, right) -> left to right })
                nextPos = nextPos.shift(direction)
            }
        }

        return hasMovedBoxes
    }

    private fun findBoxStacks(boxPositions: Set<Pair<Point2D, Point2D>>, direction: Direction): Set<Point2D> {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            val firstBox = boxPositions.first()
            val movableBoxPositions = mutableSetOf(firstBox.first, firstBox.second)

            var currentPosition = firstBox.second

            while(getTile(currentPosition, WarehouseTile('?')).isObstacle()) {
                val nextPosition = currentPosition.shift(direction)
                val nextTile = getTile(nextPosition, WarehouseTile('?'))

                if (nextTile.isEmpty()) {
                    return movableBoxPositions
                }

                if (nextTile.isObstacle()) {
                    movableBoxPositions.add(nextPosition)
                }

                if (nextTile.isWall()) {
                    return emptySet()
                }

                currentPosition = nextPosition
            }
        }

        if (direction == Direction.UP || direction == Direction.DOWN) {
            val farthestRow = if (direction == Direction.DOWN) boxPositions.maxBy { it.first.y } else boxPositions.minBy { it.first.y }
            val farthestRowPoints = boxPositions.filter { it.first.y == farthestRow.first.y }.flatMap { listOf(it.first, it.second) }
            val xRange = farthestRowPoints.minBy { it.x }.x to farthestRowPoints.maxBy { it.x }.x
            val searchRange = (xRange.first - 1)..(xRange.second + 1)
            val nextRowPositions = searchRange.map { x -> Point2D(x, farthestRow.first.y).shift(direction) }
                .associateWith { getTile(it, WarehouseTile('?')) }

            if (nextRowPositions.values.all { it.isEmpty() }) {
                return nextRowPositions.keys
            }

            return if (nextRowPositions.values.any { it.isWall() }) {
                emptySet()
            } else {
                val wholeBoxes = mutableSetOf<Pair<Point2D, Point2D>>()
                nextRowPositions.entries.windowed(2) { (left, right) ->
                    if (left.value.isBoxStart() && right.value.isBoxEnd()) {
                        wholeBoxes.add(left.key to right.key)
                    }
                }

                findBoxStacks(wholeBoxes, direction)
            }
        }

        throw IllegalArgumentException("Invalid direction for warehouse robot: $direction")
    }
}