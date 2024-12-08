package io.github.tomplum.aoc.city

import io.github.tomplum.libs.extensions.cartesianProduct
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.sqrt

class CityMap(data: List<String>): AdventMap2D<CityTile>() {
    private val xMax: Int
    private val xMin: Int
    private val yMax: Int
    private val yMin: Int

    init {
        init(data) { value ->
            CityTile(value as Char)
        }

        xMax = xMax()!!
        xMin = xMin()!!
        yMin = yMin()!!
        yMax = yMax()!!
    }

    fun getAntiNodes(): Int {
        val antiNodes = mutableSetOf<Point2D>()

        filterTiles { tile -> tile.isAntenna() }
            .entries
            .groupBy { it.value.value }
            .forEach { (frequency, antennas) ->
                val antennaPositions = antennas.map { it.key }
                val combinations = antennaPositions.cartesianProduct(antennaPositions).filterNot { it.first == it.second }
                combinations.forEach { (first, second) ->
                    val dx = second.x - first.x.toDouble()
                    val dy = second.y - first.y
                    val distance = sqrt(dx * dx + dy * dy)

                    val unitDx = dx / distance
                    val unitDy = dy / distance

                    val scaledDx = unitDx * distance
                    val scaledDy = unitDy * distance

                    val firstAntiNode = Point2D(first.x - scaledDx.toInt(), first.y - scaledDy.toInt())
                    val secondAntiNode = Point2D(second.x + scaledDx.toInt(), second.y + scaledDy.toInt())

                    antiNodes.add(firstAntiNode)
                    antiNodes.add(secondAntiNode)
                }
            }
        return antiNodes.count { it.isValidAntiNodePosition() }
    }

    private fun Point2D.isValidAntiNodePosition(): Boolean {
        val isWithinMapBounds = this.x >= xMin && this.x <= xMax && this.y >= yMin && this.y <= yMax
//        val isNotOverlappingAntenna = filterTiles { it.isAntenna() }.none { it.key == this }
        return isWithinMapBounds
    }
}