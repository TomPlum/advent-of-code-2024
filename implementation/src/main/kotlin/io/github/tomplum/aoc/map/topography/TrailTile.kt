package io.github.tomplum.aoc.map.topography

import io.github.tomplum.libs.math.map.MapTile

class TrailTile(val height: Int): MapTile<Int>(height) {
    fun isTrailHead() = height == 0

    fun isTrailEnd() = height == 9
}