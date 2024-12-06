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

    fun simulateGuardPatrol(): List<Point2D> {
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

        return tilesSeen.distinct()
    }

    fun simulateGuardPatrolTimeLoops(): Int{
        val guardStart = filterTiles { it.isGuard() }.entries.first()

        var guardPosition = guardStart.key
        var guardDirection = guardStart.value.guardDirection()

        val guardPatrolPathPositions = simulateGuardPatrol().filterNot { it == guardStart.key }

        val guardStatesSeen = mutableListOf<String>()
        var timeLoopsSeen = 0

        guardPatrolPathPositions.forEach { guardPatrolPathPosition ->
            // Add an obstruction to the position on the guards path
            addTile(guardPatrolPathPosition, LabTile('#'))

            try {
                val guardLeavingLab = isGuardLeavingLab(guardPosition, guardDirection)

                while (!guardLeavingLab) {
                    if (guardStatesSeen.contains("$guardPosition$guardDirection")) {
                        // If we've seen this state before, we're in a time loop
                        timeLoopsSeen++
                        break
                    }

                    guardStatesSeen.add("$guardPosition$guardDirection")
//                    addTile(guardPosition, LabTile('O'))

                    val posInFrontOfGuard = guardPosition.shift(guardDirection)
                    val tileInFrontOfGuard = getTile(posInFrontOfGuard)

                    if (tileInFrontOfGuard.isObstruction()) {
                        guardDirection = guardDirection.rotate(270)
                    } else {
                        guardPosition = guardPosition.shift(guardDirection)
                    }
                }
            } catch (e: IllegalArgumentException) {
                // We've gone off the map, so this has not created a loop
            }

            guardPosition = guardStart.key
            guardDirection = guardStart.value.guardDirection()
            guardStatesSeen.clear()
            addTile(guardPatrolPathPosition, LabTile('.'))
        }

        return timeLoopsSeen
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