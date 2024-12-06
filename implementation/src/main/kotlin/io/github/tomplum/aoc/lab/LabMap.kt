package io.github.tomplum.aoc.lab

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class LabMap(data: List<String>): AdventMap2D<LabTile>() {
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
    }

    fun followGuardRoute(): Int {
        var (guardPosition, guardTile) = filterTiles { it.isGuard() }.entries.first()
        val exitY = yMax()!!

        val tilesSeen = mutableSetOf(guardPosition)

        while (guardPosition.y != exitY || guardTile.guardDirection() != Direction.UP) {
            val posInFrontOfGuard = guardPosition.shift(guardTile.guardDirection())
            val tileInFrontOfGuard = getTile(posInFrontOfGuard, LabTile('#'))

            if (tileInFrontOfGuard.isObstruction()) {
                guardTile = LabTile.fromDirection(guardTile.guardDirection().rotate(270))
            } else {
                guardPosition = guardPosition.shift(guardTile.guardDirection())
                tilesSeen.add(guardPosition)
            }
        }

        return tilesSeen.size
    }
}