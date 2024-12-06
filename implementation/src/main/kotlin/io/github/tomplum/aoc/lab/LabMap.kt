package io.github.tomplum.aoc.lab

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class LabMap(data: List<String>): AdventMap2D<LabTile>() {
    private val xMax: Int
    private val xMin: Int
    private val yMax: Int
    private val yMin: Int

    init {
        var x = 0
        var y = 0

        data.forEach { row ->
            row.forEach { column ->
                val tile = LabTile(column)
                val position = Point2D(x, y)

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }

        xMax = xMax()!!
        xMin = xMin()!!
        yMin = yMin()!!
        yMax = yMax()!!
    }

    fun simulateGuardPatrol(): Int {
        val guardStart = filterTiles { it.isGuard() }.entries.first()
        var guardPosition = guardStart.key
        var guardDirection = guardStart.value.guardDirection()

        val tilesSeen = mutableListOf(guardPosition)

        while (!isGuardLeavingLab(guardPosition, guardDirection)) {
            val posInFrontOfGuard = guardPosition.shift(guardDirection)
            val tileInFrontOfGuard = getTile(posInFrontOfGuard)

            if (tileInFrontOfGuard.isObstruction()) {
                guardDirection = guardDirection.rotate(270)
            } else {
                guardPosition = guardPosition.shift(guardDirection)
                tilesSeen.add(guardPosition)
            }

        }

        return tilesSeen.distinct().size
    }

    private fun isGuardLeavingLab(position: Point2D, direction: Direction): Boolean {
        if (direction == Direction.UP) {
            return position.y == yMax
        }

        if (direction == Direction.DOWN) {
            return position.y == yMin
        }

        if (direction == Direction.LEFT) {
            return position.x == xMin
        }

        if (direction == Direction.RIGHT) {
            return position.x == xMax
        }

        throw IllegalArgumentException("Invalid guard direction $direction")
    }
}