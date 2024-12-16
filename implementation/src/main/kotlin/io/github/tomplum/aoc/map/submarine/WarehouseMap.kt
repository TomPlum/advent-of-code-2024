package io.github.tomplum.aoc.map.submarine

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

interface WarehouseMap {
    fun getRobotPosition(): Point2D

    fun getWarehouseTile(position: Point2D): WarehouseTile

    fun moveRobot(currentPosition: Point2D, direction: Direction): Point2D

    fun getBoxes():  Map<Point2D, WarehouseTile>

    fun moveObstacle(robotPosition: Point2D, direction: Direction): Boolean
}