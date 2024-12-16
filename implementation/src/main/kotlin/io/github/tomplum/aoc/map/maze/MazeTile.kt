package io.github.tomplum.aoc.map.maze

import io.github.tomplum.libs.math.map.MapTile

class MazeTile(override val value: Char): MapTile<Char>(value) {
    fun isReindeerStart() = value == 'S'
    fun isEnd() = value == 'E'
    fun isWall() = value == '#'
    fun isTraversable() = value == '.' || isReindeerStart()
}