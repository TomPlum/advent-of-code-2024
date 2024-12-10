package io.github.tomplum.aoc.map.topography

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class HikingTrailMap(data: List<String>): AdventMap2D<TrailTile>() {
    init {
        init(data) { tile ->
            TrailTile((tile as Char).digitToInt())
        }
    }

    fun getTrailHeadScoreSum(): Int = filterTiles { it.isTrailHead() }.keys
        .sumOf { startingPoint -> findTrailEnds(setOf(startingPoint)) }

    fun getTrailHeadRatingSum(): Int = filterTiles { it.isTrailHead() }.keys
        .sumOf { startingPoint -> findTrailEndsRating(setOf(startingPoint)) }

    private fun findTrailEnds(next: Set<Point2D>, visited: MutableSet<Point2D> = mutableSetOf()): Int {
        return next.fold(0) { trailsEnds, position ->
            if (position in visited) {
                trailsEnds
            } else {
                visited.add(position)

                val currentTile = getTile(position, TrailTile(-1))

                if (currentTile.isTrailEnd()) {
                    trailsEnds + 1
                } else {
                    val nextTrailPositions = position.orthogonallyAdjacent()
                        .asSequence()
                        .filter { it !in visited }
                        .map { it to getTile(it, TrailTile(-1)) }
                        .filter { (_, tile) -> tile.height == currentTile.height + 1 }
                        .map { (pos, _) -> pos }
                        .toSet()

                    trailsEnds + findTrailEnds(nextTrailPositions, visited)
                }
            }
        }
    }

    private fun findTrailEndsRating(next: Set<Point2D>): Int {
        return next.sumOf { position ->
            val currentTile = getTile(position, TrailTile(-1))

            if (currentTile.isTrailEnd()) {
                1
            } else {
                val nextTrailPositions = position.orthogonallyAdjacent()
                    .map { it to getTile(it, TrailTile(-1)) }
                    .filter { (_, tile) -> tile.height == currentTile.height + 1 }
                    .map { (pos, _) -> pos }
                    .toSet()

                findTrailEndsRating(nextTrailPositions)
            }
        }
    }
}