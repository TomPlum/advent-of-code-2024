package io.github.tomplum.aoc.stones

class PlutonianPebbles(arrangement: String) {

    private var stones = arrangement.split(" ").map { it.toLong() }

    fun blink(times: Int): Int {
        repeat(times) { rearrange() }
        return stones.size
    }

    private fun rearrange() {
        stones = stones.flatMap { stone ->
            if (stone == 0L) {
                listOf(1L)
            } else {
                val stoneString = stone.toString()
                if (stoneString.length % 2 == 0) {
                    val middleIndex = stoneString.length / 2
                    val left = stoneString.substring(0, middleIndex).toLong()
                    val right = stoneString.substring(middleIndex).toLong()
                    listOf(left, right)
                } else {
                    listOf(stone * 2024L)
                }
            }
        }
    }
}