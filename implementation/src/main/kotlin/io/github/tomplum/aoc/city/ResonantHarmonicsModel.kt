package io.github.tomplum.aoc.city

import io.github.tomplum.libs.extensions.cartesianProduct
import io.github.tomplum.libs.math.point.Point2D

class ResonantHarmonicsModel: AntennaScanningModel() {
    override fun scan(antennas: List<Map.Entry<Point2D, CityTile>>, cityBoundary: Pair<Point2D, Point2D>): List<Point2D> {
        val antennaPositions = antennas.map { (position) -> position }

        val combinations = antennaPositions
            .cartesianProduct(antennaPositions)
            .filterNot { (first, second) -> first == second }

        return combinations.flatMap { (first, second) ->
            val antiNodes = mutableListOf<Point2D>()
            var positions = getAntiNodePairLocations(first, second)
            var interval = 1

            while(positions.toList().any { position -> isWithinCityBoundary(position, cityBoundary) }) {
                if (isWithinCityBoundary(positions.first, cityBoundary)) {
                    antiNodes.add(positions.first)
                }

                if (isWithinCityBoundary(positions.second, cityBoundary)) {
                    antiNodes.add(positions.second)
                }

                positions = getAntiNodePairLocations(first, second, interval)
                interval++
            }

            antiNodes + antennaPositions
        }
    }
}