package io.github.tomplum.aoc.map.topography

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class HikingTrailMap(data: List<String>): AdventMap2D<TrailTile>() {
    init {
        init(data) { tile ->
            TrailTile((tile as Char).digitToInt())
        }
    }

    fun findTrailheads(): Int = filterTiles { it.isTrailHead() }
        .keys
        .sumOf { startingPoint ->
            findTrailEnds(setOf(startingPoint))
        }

    private fun findTrailEnds(next: Set<Point2D>, visited: MutableSet<Point2D> = mutableSetOf()): Int {
        var count = 0

        for (position in next) {
            if (position in visited) continue
            visited.add(position)

            val currentTile = getTile(position, TrailTile(-1))

            if (currentTile.isTrailEnd()) {
                count += 1
            } else {
                val nextTrailPositions = position.orthogonallyAdjacent()
                    .asSequence()
                    .filter { it !in visited }
                    .map { it to getTile(it, TrailTile(-1)) }
                    .filter { (_, tile) -> tile.height == currentTile.height + 1 }
                    .map { (pos, _) -> pos }
                    .toSet()

                count += findTrailEnds(nextTrailPositions, visited)
            }
        }

        return count
    }
}