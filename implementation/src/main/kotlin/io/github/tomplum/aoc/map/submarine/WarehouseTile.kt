package io.github.tomplum.aoc.map.submarine

import io.github.tomplum.libs.math.map.MapTile

class WarehouseTile(override val value: Char): MapTile<Char>(value) {
    fun isWall() = value == '#'
    fun isObstacle() = value == 'O'
    fun isRobot() = value == '@'
    fun isEmpty() = value == '.'
}