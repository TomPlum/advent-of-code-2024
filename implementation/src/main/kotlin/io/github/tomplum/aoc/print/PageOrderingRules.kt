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

    fun fix(update: PageUpdate): PageUpdate {
        val pageNumbers = update.pageNumbers.toMutableList()

        val relatedRules = rules.filter { rule ->
            pageNumbers.contains(rule.first) && pageNumbers.contains(rule.second)
        }

        var fixed = false

        while (!fixed) {
            relatedRules.forEach { rule ->
                val firstPosition = pageNumbers.indexOf(rule.first)
                val secondPosition = pageNumbers.indexOf(rule.second)

                if (firstPosition > secondPosition) {
                    val firstValue = pageNumbers[firstPosition]
                    pageNumbers[firstPosition] = pageNumbers[secondPosition]
                    pageNumbers[secondPosition] = firstValue
                }
            }

            if (conforms(PageUpdate(pageNumbers.joinToString(",")))) {
                fixed = true
            }
        }

        return PageUpdate(pageNumbers.joinToString(","))
    }

    private data class PageRule(private val rule: String) {
        val first = rule.split("|").first().toInt()
        val second = rule.split("|").last().toInt()
    }
}