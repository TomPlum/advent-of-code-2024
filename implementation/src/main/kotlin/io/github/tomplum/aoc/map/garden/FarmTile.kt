package io.github.tomplum.aoc.map.garden

import io.github.tomplum.libs.math.map.MapTile

class FarmTile(val plant: Char): MapTile<Char>(plant) {
    fun isPlotted() = plant == '^'
}