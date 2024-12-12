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
        val seen = mutableSetOf<Point2D>()

        while(getDataMap().any { !it.value.isPlotted() }) {
            val (pos, tile) = findTile { !it.isPlotted() } ?: continue
            val currentPlotPlant = tile.plant
            val gardenPlotPoints = exploreGardenPlot(pos, currentPlotPlant)

            plots.merge(currentPlotPlant, setOf(gardenPlotPoints)) { old, new -> old + new }
            seen.addAll(gardenPlotPoints)
            gardenPlotPoints.forEach {
                addTile(it, FarmTile('^'))
            }
        }

        return plots.values.sumOf { plotList ->
            plotList.sumOf { plot ->
                val area = plot.size
                val perimeter = calculatePerimeter(plot)
                area * perimeter
            }
        }
    }

    /**
     * TODO: Move to libs
     */
    private fun calculatePerimeter(points: Set<Point2D>): Int {
        return points.fold(0) { perimeter, point ->
            perimeter + point.orthogonallyAdjacent().fold(4) { edges, neighbour ->
                if (neighbour in points) edges - 1 else edges
            }
        }
    }

    private fun exploreGardenPlot(start: Point2D, plantId: Char): Set<Point2D> {
        val gardenPlotPoints = mutableSetOf<Point2D>()
        val next = Stack<Point2D>()
        val seen = mutableSetOf<Point2D>()

        next.add(start)

        while (next.isNotEmpty()) {
            val position = next.pop()

            if (!seen.add(position)) {
                continue
            }

            if (getTile(position, FarmTile('?')).plant == plantId) {
                gardenPlotPoints.add(position)

                position.orthogonallyAdjacent().filter { adjacent ->
                    val notSeen = adjacent !in seen
                    val withinPlot = getTile(adjacent, FarmTile('?')).plant == plantId
                    notSeen && withinPlot
                }.forEach { next.add(it) }
            }
        }

        return gardenPlotPoints
    }
}