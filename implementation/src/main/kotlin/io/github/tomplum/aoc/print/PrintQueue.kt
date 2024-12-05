package io.github.tomplum.aoc.print

class PrintQueue(private val data: List<String>) {

    fun analyse(): Int {
        val (rules, updates) = data.split { it.isBlank() }.let {
            Pair(PageOrderingRules(it.first().toList()), it.last().map { PageUpdate(it) })
        }

        val conformingUpdates = updates.filter { update ->
            rules.conforms(update)
        }

        return conformingUpdates.sumOf { update ->
            update.pageNumbers[update.pageNumbers.size / 2]
        }
    }

    /**
     * TODO: Consume from lib once released
     */
    private fun <T> Collection<T>.split(predicate: (element: T) -> Boolean): Collection<Collection<T>> {
        var i = 0
        val data = mutableMapOf<Int, List<T>>()
        var current = mutableListOf<T>()

        this.forEachIndexed { index, element ->
            if (index == this.size - 1) {
                current.add(element)
                data[i] = current
            } else if (predicate(element)) {
                data[i] = current
                i++
                current = mutableListOf()
            } else {
                current.add(element)
            }
        }

        return data.values.toList()
    }
}