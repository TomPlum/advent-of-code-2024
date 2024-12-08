package io.github.tomplum.aoc.city

import io.github.tomplum.libs.extensions.cartesianProduct
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.roundToInt
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

    fun getAntiNodes(): Int = filterTiles { tile -> tile.isAntenna() }
        .entries
        .groupBy { it.value.value }
        .values
        .flatMap { antennas ->
            val antennaPositions = antennas.map { it.key }

            val combinations = antennaPositions
                .cartesianProduct(antennaPositions)
                .filterNot { it.first == it.second }

            combinations.flatMap { (first, second) ->
                findAntiNodes(first, second).toList()
            }
        }
        .distinct()
        .count { it.isValidAntiNodePosition() }

    fun getAntiNodesWithHarmonicFrequency(): Int =  filterTiles { tile -> tile.isAntenna() }
        .entries
        .groupBy { it.value.value }
        .values
        .flatMap { antennas ->
            val antennaPositions = antennas.map { it.key }

            val combinations = antennaPositions
                .cartesianProduct(antennaPositions)
                .filterNot { it.first == it.second }

            combinations.flatMap { (first, second) ->
                val antiNodes = mutableSetOf<Point2D>()
                var positions = findAntiNodes(first, second)
                var interval = 1

                while(positions.toList().any { it.isValidAntiNodePosition() }) {
                    if (positions.first.isValidAntiNodePosition()) {
                        antiNodes.add(positions.first)
                        addTile(positions.first, CityTile('#'))
                    }

                    if (positions.second.isValidAntiNodePosition()) {
                        antiNodes.add(positions.second)
                        addTile(positions.second, CityTile('#'))
                    }

                    positions = findAntiNodes(first, second, interval)
                    interval++
                }

                antiNodes
            }
        }
        .distinct()
        .let { antiNodes -> antiNodes + filterTiles { it.isAntenna() }.keys }
        .count { it.isValidAntiNodePosition() }

    private fun findAntiNodes(first: Point2D, second: Point2D, interval: Int = 1): Pair<Point2D, Point2D> {
        val dx = second.x - first.x.toDouble()
        val dy = second.y - first.y
        val distance = sqrt(dx * dx + dy * dy)

        val unitDx = dx / distance
        val unitDy = dy / distance

        val offsetX = interval * distance * unitDx
        val offsetY = interval * distance * unitDy

        val firstAntiNode = Point2D(first.x - offsetX.roundToInt(), first.y - offsetY.roundToInt())
        val secondAntiNode = Point2D(second.x + offsetX.roundToInt(), second.y + offsetY.roundToInt())

        return firstAntiNode to secondAntiNode
    }

    private fun Point2D.isValidAntiNodePosition(): Boolean {
        return this.x >= xMin && this.x <= xMax && this.y >= yMin && this.y <= yMax
    }
}