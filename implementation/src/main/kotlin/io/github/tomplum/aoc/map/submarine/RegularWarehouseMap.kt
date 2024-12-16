package io.github.tomplum.aoc.map.submarine

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class RegularWarehouseMap(data: List<String>): AdventMap2D<WarehouseTile>(), WarehouseMap {
    init {
        init(data) {
            WarehouseTile(it as Char)
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
        var hasMovementPotential = true
        var hasMovedObstacles = false
        var nextPos = robotPosition.shift(direction)
        val obstacles = mutableListOf<Point2D>()

        while(hasMovementPotential || !hasMovedObstacles) {
            val nextTile = getTile(nextPos, WarehouseTile('?'))

            if (nextTile.isEmpty()) {
                // Other obstacles in the line get shifted one.
                // Start by removing the current obstacle tiles
                obstacles.forEach { obstaclePos ->
                    addTile(obstaclePos, WarehouseTile('.'))
                }

                // Then add in the new ones shift in the current direction
                obstacles.forEach { obstaclePos ->
                    addTile(obstaclePos.shift(direction), WarehouseTile('O'))
                }

                // We've shifted so we're done with this loop
                hasMovedObstacles = true
                hasMovementPotential = false
            } else if (nextTile.isWall()) {
                break
            } else if (nextTile.isObstacle()) {
                obstacles.add(nextPos)
                nextPos = nextPos.shift(direction)
            }
        }

        return hasMovedObstacles
    }
}