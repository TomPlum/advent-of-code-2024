package io.github.tomplum.aoc.print

import io.github.tomplum.libs.extensions.split

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

    fun orderAnalyse(): Int {
        val (rules, updates) = data.split { it.isBlank() }.let {
            Pair(PageOrderingRules(it.first().toList()), it.last().map { PageUpdate(it) })
        }

        return updates
            .filterNot { update -> rules.conforms(update) }
            .map { update -> rules.fix(update) }
            .sumOf { update -> update.pageNumbers[update.pageNumbers.size / 2] }
    }
}