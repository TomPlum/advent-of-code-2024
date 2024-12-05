package io.github.tomplum.aoc.print

data class PageUpdate(private val update: String) {
    val pageNumbers = update.split(",").map { it.toInt() }
}