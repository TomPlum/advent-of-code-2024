package io.github.tomplum.aoc.robot

import io.github.tomplum.libs.math.point.Point2D

class Robot(data: String) {
    var position = data
        .split(" ")
        .first()
        .removePrefix("p=")
        .split(",")
        .let { Point2D(it[0].toInt(), it[1].toInt()) }

    var velocity = data
        .split(" ")
        .last()
        .removePrefix("v=")
        .split(",")
        .let { Point2D(it[0].toInt(), it[1].toInt()) }
}