package io.github.tomplum.aoc.map.garden

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.*

class FarmMap(data: List<String>): AdventMap2D<FarmTile>() {
    private var xMax: Int
    private var yMax: Int
    private var xMin: Int
    private var yMin: Int

    init {
        init(data) {
            FarmTile(it as Char)
        }

        xMax = xMax()!!
        yMax = yMax()!!
        xMin = xMin()!!
        yMin = yMin()!!
    }

    fun getTotalGardenPlotFenceCost(): Int {
        val plots = mutableMapOf<Char, Set<Set<Point2D>>>()
        var currentPlot = mutableSetOf<Point2D>()
        var currentPlotId = '?'
        val seen = mutableSetOf<Point2D>()

        getDataMap().forEach { (pos, tile) ->
            if (currentPlotId != '?' && tile.value != currentPlotId) {
                plots.merge(currentPlotId, setOf(currentPlot)) { old, new -> old + new }
                currentPlot = mutableSetOf()
            }

            currentPlotId = tile.value

            val adjacent = getAdjacentLines(pos, currentPlotId)

            currentPlot.addAll(adjacent + pos)
            seen.addAll(adjacent + pos)
        }

        return plots.values.sumOf { plotList ->
            plotList.sumOf { plot ->
                val area = plot.size
                val perimeter = when(area) {
                    1 -> 4
                    2 -> 8
                    else -> calculatePerimeter(plot)
                }
                area * perimeter
            }
        }
    }

    /**
     * TODO: Move to libs (and functions below)
     */
    private fun calculatePerimeter(points: Set<Point2D>): Int {
        var perimeter = 0

        for (point in points) {
            var edges = 4

            point.orthogonallyAdjacent().forEach { neighbour ->
                if (neighbour in points) {
                    edges--
                }
            }

            perimeter += edges
        }

        return perimeter
    }

    private fun getAdjacentLines(start: Point2D, id: Char): Set<Point2D> {
        val points = mutableSetOf<Point2D>()
        val next = Stack<Point2D>()
        val seen = mutableSetOf<Point2D>()
        next.add(start)

        while(next.isNotEmpty()) {
            val position = next.pop()
            seen.add(position)

            if (getTile(position, FarmTile('?')).value == id) {
                points.add(position)
            }

            val candidates = filterPoints(position.orthogonallyAdjacent().toSet())
                .filter { it.value.value == id }
                .filter { hasRecorded(it.key) }
                .filter { !seen.contains(it.key) }

            next.addAll(candidates.keys)
        }

        return points
    }
}