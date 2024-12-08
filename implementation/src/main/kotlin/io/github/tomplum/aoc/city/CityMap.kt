package io.github.tomplum.aoc.city

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class CityMap(data: List<String>): AdventMap2D<CityTile>() {
    private val cityBoundary: Pair<Point2D, Point2D>

    init {
        init(data) { value ->
            CityTile(value as Char)
        }

        cityBoundary = Pair(Point2D(xMin()!!, yMin()!!), Point2D(xMax()!!, yMax()!!))
    }

    fun getAntiNodes(model: AntennaScanningModel): Int = filterTiles { tile -> tile.isAntenna() }
        .entries
        .groupBy {(_, tile) -> tile.value }
        .values
        .flatMap { antennas -> model.scan(antennas, cityBoundary) }
        .distinct()
        .count()
}