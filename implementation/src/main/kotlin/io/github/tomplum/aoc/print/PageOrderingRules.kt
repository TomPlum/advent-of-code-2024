package io.github.tomplum.aoc.print

data class PageOrderingRules(private val data: List<String>) {
    private val rules = data.map { line -> PageRule(line) }

    fun conforms(update: PageUpdate): Boolean {
        val pageNumbers = update.pageNumbers
        val relatedRules = rules.filter { rule ->
            pageNumbers.contains(rule.first) && pageNumbers.contains(rule.second)
        }

        return relatedRules.all { rule ->
            val firstPosition = pageNumbers.indexOf(rule.first)
            val secondPosition = pageNumbers.indexOf(rule.second)
            firstPosition < secondPosition
        }
    }

    private data class PageRule(private val rule: String) {
        val first = rule.split("|").first().toInt()
        val second = rule.split("|").last().toInt()
    }
}