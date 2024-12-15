package io.github.tomplum.aoc.map.submarine

import io.github.tomplum.libs.extensions.split
import io.github.tomplum.libs.math.Direction.*

class WarehouseSimulator(instructions: List<String>) {

    private val map = WarehouseMap(instructions.split { it.isBlank() }.first().toList())
    private val directions = instructions.split { it.isBlank() }.last().flatMap { it.map { it.toDirection() } }

    fun simulate(): Int {
        var robotPosition = map.getRobotPosition()

        directions.forEach { direction ->
            val nextPositionTile = map.getWarehouseTile(robotPosition.shift(direction))

            if (nextPositionTile.isEmpty()) {
                robotPosition = map.moveRobot(robotPosition, direction)
            } else if (nextPositionTile.isObstacle()) {
                val moved = map.moveObstacle(robotPosition, direction)
                if (moved) {
                    robotPosition = map.moveRobot(robotPosition, direction)
                }
            }
        }

        return map
            .getBoxes()
            .keys
            .sumOf { it.x + (it.y * 100) }
    }

    private fun Char.toDirection() = when (this) {
        '^' -> DOWN
        '>' -> RIGHT
        'v' -> UP
        '<' -> LEFT
        else -> throw IllegalArgumentException("Invalid Direction String: $this")
    }
}