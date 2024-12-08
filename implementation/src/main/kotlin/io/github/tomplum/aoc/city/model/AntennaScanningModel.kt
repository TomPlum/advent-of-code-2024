package io.github.tomplum.aoc.city.model

import io.github.tomplum.aoc.city.CityTile
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.roundToInt

abstract class AntennaScanningModel {
    /**
     * Scans a [CityMap] and finds the locations
     * of all valid anti-node positions relative
     * to the positions of any known antennas,
     * their frequencies and the given model.
     *
     * @param antennas A list of known antennas positions and their frequencies.
     * @param cityBoundary Coordinates of the top-left and bottom-right of the city boundary.
     *
     * @return A list of unique anti-node positions within the city boundary.
     */
    abstract fun scan(
        antennas: List<Map. Entry<Point2D, CityTile>>,
        cityBoundary: Pair<Point2D, Point2D>
    ): List<Point2D>

    /**
     * Finds a pair of possible anti-node location candidates relative
     * to the [first] and [second] antenna locations at the given [interval].
     *
     * These locations are an extrapolation based on a hypothetical straight
     * line drawn directly through the [first] and [second] antenna positions.
     *
     * The [interval] determines how far along the line the anti-node positions
     * should be found for. This is calculated based on the Manhattan distance
     * between the two antennas. For example, if the two antennas are 3 away
     * from one-another, then interval (I) would be distance (D) on each side
     * of the two antennas, and so forth (I=2 -> D=6), (I=3, D=9) etc.
     *
     * Such locations may not be within the city boundaries and should be
     * validated after-wards.
     *
     * @param first The first antenna location.
     * @param second The second antenna location.
     * @param interval How many distance intervals along the resultant line to get anti-nodes for.
     * @returns The locations of the two anti-nodes along the antenna line.
     */
    protected fun getAntiNodePairLocations(first: Point2D, second: Point2D, interval: Int = 1): Pair<Point2D, Point2D> {
        val dx = second.x - first.x.toDouble()
        val dy = second.y - first.y
        val distance = first.distanceBetween(second).toDouble()

        val unitDx = dx / distance
        val unitDy = dy / distance

        val offsetX = interval * distance * unitDx
        val offsetY = interval * distance * unitDy

        val firstAntiNode = Point2D(first.x - offsetX.roundToInt(), first.y - offsetY.roundToInt())
        val secondAntiNode = Point2D(second.x + offsetX.roundToInt(), second.y + offsetY.roundToInt())

        return firstAntiNode to secondAntiNode
    }

    protected fun isWithinCityBoundary(position: Point2D, boundary: Pair<Point2D, Point2D>): Boolean {
        val (xMin, yMin) = boundary.first
        val (xMax, yMax) = boundary.second
        return position.x in xMin..xMax && position.y >= yMin && position.y <= yMax
    }
}