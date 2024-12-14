package io.github.tomplum.aoc.robot

import io.github.tomplum.libs.extensions.product
import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.map.MapTile
import io.github.tomplum.libs.math.point.Point2D

class Tile(value: Char): MapTile<Char>(value)
class Map(robots: List<Robot>): AdventMap2D<Tile>() {
    init {
        robots.forEach {
            addTile(Point2D(it.position.x, it.position.y), Tile('1'))
        }
    }
}

class BathroomSecuritySimulator(robotData: List<String>) {
    private val robots = robotData.map { Robot(it) }

    fun simulate(seconds: Int, width: Int, height: Int): Int {
        repeat(seconds) {
            robots.forEach { robot ->
                val (x, y) = robot.position
                val (xv, yv) = robot.velocity
                val xNew = ((x + xv) % width + width) % width
                val yNew = ((y + yv) % height + height) % height
                robot.position = Point2D(xNew, yNew)
            }
        }

        val halfWidth = width / 2
        val halfHeight = height / 2

        // (0, 0), (4, 2)
        val topLeft = Point2D(0, 0) to Point2D(halfWidth - 1, halfHeight - 1)
        // (6, 0), (10, 2)
        val topRight = Point2D(halfWidth + 1, 0) to Point2D(width - 1, halfHeight - 1)
        // (0, 4), (4 , 6)
        val bottomLeft = Point2D(0, halfHeight + 1) to Point2D(halfWidth - 1, height - 1)
        // (6, 4), (10, 6)
        val bottomRight = Point2D(halfWidth + 1, halfHeight + 1) to Point2D(width - 1, height - 1)

        val quadrants = mutableMapOf<Int, List<Robot>>()

        robots.forEach { robot ->
            if (robot.isWithinQuadrant(topLeft)) {
                quadrants.merge(1, listOf(robot)) { a, b -> a + b }
            }

            if (robot.isWithinQuadrant(topRight)) {
                quadrants.merge(2, listOf(robot)) { a, b -> a + b }
            }

            if (robot.isWithinQuadrant(bottomLeft)) {
                quadrants.merge(3, listOf(robot)) { a, b -> a + b }
            }

            if (robot.isWithinQuadrant(bottomRight)) {
                quadrants.merge(4, listOf(robot)) { a, b -> a + b }
            }
        }

        val robotsInQuadrants = quadrants.values.flatten().size

        val map = Map(robots)
        AdventLogger.info(map)

        return quadrants.map { it.value.size }.product() as Int
    }

    private fun Robot.isWithinQuadrant(bounds: Pair<Point2D, Point2D>): Boolean = this.position.x >= bounds.first.x
            && this.position.x <= bounds.second.x
            && this.position.y >= bounds.first.y
            && this.position.y <= bounds.second.y

}