package io.github.tomplum.aoc.city

import io.github.tomplum.libs.math.map.MapTile

class CityTile(override val value: Char): MapTile<Char>(value) {
    fun isAntenna() = Regex("^[a-zA-Z0-9]\$").matches(value.toString())
}