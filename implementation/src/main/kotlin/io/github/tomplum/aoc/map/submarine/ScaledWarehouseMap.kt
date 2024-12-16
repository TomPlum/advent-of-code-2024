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
        val firstBoxPosition = robotPosition.shift(direction)
        val firstBoxPieceTile = getTile(firstBoxPosition, WarehouseTile('?'))

        val firstBoxSecondPosition = if (direction == Direction.UP || direction == Direction.DOWN) {
            if (firstBoxPieceTile.isBoxStart()) {
                firstBoxPosition.shift(Direction.RIGHT)
            } else {
                firstBoxPosition.shift(Direction.LEFT)
            }
        } else {
            if (firstBoxPieceTile.isBoxStart()) {
                firstBoxPosition.shift(Direction.RIGHT)
            } else {
                firstBoxPosition.shift(Direction.LEFT)
            }
        }
        val firstBoxSecondPieceTile = getTile(firstBoxSecondPosition, WarehouseTile('?'))

        val boxPositions = findBoxStacks(
            mapOf(
                firstBoxPosition to firstBoxPieceTile.value,
                firstBoxSecondPosition to firstBoxSecondPieceTile.value
            ),
            direction
        )

        if (boxPositions.isEmpty()) {
            return false
        }


        boxPositions.forEach { (pos) ->
            addTile(pos, WarehouseTile('.'))
        }

        boxPositions.forEach { (pos, tileValue) ->
            addTile(pos.shift(direction), WarehouseTile(tileValue))
        }

        return true
    }

    private fun findBoxStacks(boxPositions: Map<Point2D, Char>, direction: Direction): Map<Point2D, Char> {
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            val movableBoxPositions = mutableMapOf<Point2D, Char>()
            boxPositions.entries.forEach { movableBoxPositions[it.key] = it.value }

            var currentPosition = boxPositions.keys.first()

            while(getTile(currentPosition, WarehouseTile('?')).isBox()) {
                val nextPosition = currentPosition.shift(direction)
                val nextTile = getTile(nextPosition, WarehouseTile('?'))

                if (nextTile.isEmpty()) {
                    return movableBoxPositions
                }

                if (nextTile.isObstacle()) {
                    movableBoxPositions[nextPosition] = nextTile.value
                }

                if (nextTile.isWall()) {
                    return emptyMap()
                }

                currentPosition = nextPosition
            }

            return emptyMap()
        }

        if (direction == Direction.UP || direction == Direction.DOWN) {
            val farthestRow = if (direction == Direction.DOWN) boxPositions.keys.minBy { it.y } else boxPositions.keys.maxBy { it.y }
            val farthestRowPoints = boxPositions.keys.filter { it.y == farthestRow.y }
            val xRange = farthestRowPoints.minBy { it.x }.x to farthestRowPoints.maxBy { it.x }.x
            val searchRange = (xRange.first - 1)..(xRange.second + 1)
            val nextRowPositions = searchRange.map { x -> Point2D(x, farthestRow.y).shift(direction) }
                .associateWith { getTile(it, WarehouseTile('?')) }

            if (nextRowPositions.values.all { it.isEmpty() }) {
                return boxPositions.entries.associate { it.key to it.value }
            }

            return if (nextRowPositions.values.any { it.isWall() }) {
                emptyMap()
            } else {
                val next = nextRowPositions.entries.associate { (position, tile) ->
                    position to tile.value
                }

                findBoxStacks(next + boxPositions, direction)
            }
        }

        throw IllegalArgumentException("Invalid direction for warehouse robot: $direction")
    }
}