package io.github.tomplum.aoc.lab

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.MapTile

class LabTile(override val value: Char): MapTile<Char>(value) {
    fun isGuard() = listOf('^', '>', 'v', '<').contains(value)

    fun guardDirection() = when(value) {
        '^' -> Direction.DOWN
        'v' -> Direction.UP
        '<' -> Direction.LEFT
        '>' -> Direction.RIGHT
        else -> throw IllegalStateException("This tile is not a guard. It's value is $value.")
    }

    fun isObstruction() = value == '#'

    fun isTraversable() = value == '.'
}