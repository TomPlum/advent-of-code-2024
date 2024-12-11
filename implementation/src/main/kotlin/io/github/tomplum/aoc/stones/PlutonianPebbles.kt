package io.github.tomplum.aoc.stones

import kotlin.math.pow

class PlutonianPebbles(arrangement: String) {

    private var stones = arrangement.split(" ").map { it.toLong() }
    private val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun blink(times: Int): Long = stones.sumOf { stone ->
        rearrange(stone, times)
    }

    private fun rearrange(stone: Long, blink: Int): Long = cache.getOrPut(stone to blink) {
        if (blink == 0) {
            1
        } else if (stone == 0L) {
            listOf(1L)
        } else {
            val stoneString = stone.toString()
            if (stoneString.length % 2 == 0) {
                val length = stone.toString().length
                val middlePower = 10.0.pow((length / 2).toDouble()).toLong()

                val left = stone / middlePower
                val right = stone % middlePower
                listOf(left, right)
            } else {
                listOf(stone * 2024L)
            }
        }.sumOf {
            rearrange(it, blink - 1)
        }
    }
}