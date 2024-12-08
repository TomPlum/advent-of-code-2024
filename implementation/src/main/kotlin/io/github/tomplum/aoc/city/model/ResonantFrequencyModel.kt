package io.github.tomplum.aoc.city.model

import io.github.tomplum.aoc.city.CityTile
import io.github.tomplum.libs.extensions.cartesianProduct
import io.github.tomplum.libs.math.point.Point2D

class ResonantFrequencyModel: AntennaScanningModel() {
    override fun scan(antennas: List<Map.Entry<Point2D, CityTile>>, cityBoundary: Pair<Point2D, Point2D>): List<Point2D> {
        val antennaPositions = antennas.map { (position) -> position }

        val combinations = antennaPositions
            .cartesianProduct(antennaPositions)
            .filterNot { (first, second) -> first == second }

        return combinations
            .flatMap { (first, second) -> getAntiNodePairLocations(first, second).toList() }
            .filter { position -> isWithinCityBoundary(position, cityBoundary)
        }
    }
}