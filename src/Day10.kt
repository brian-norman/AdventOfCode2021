fun main() {
    val bracketMatches = hashMapOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))

    fun part1(input: List<String>): Int {
        var errorScore = 0
        val errorScoreTable = hashMapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))

        for (line in input) {
            val stack = mutableListOf<Char>()
            for (bracket in line) {
                if (bracket in bracketMatches.keys) {
                    stack.add(bracket)
                } else {
                    if (bracketMatches[stack.last()] != bracket) {
                        errorScore += errorScoreTable[bracket]!!
                        break
                    } else {
                        stack.removeLast()
                    }
                }
            }
        }

        return errorScore
    }

    fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        val scoreTable = hashMapOf(Pair('(', 1L), Pair('[', 2L), Pair('{', 3L), Pair('<', 4L))

        val lines = input.map { it }.toMutableList()
        for (line in input) {
            val stack = mutableListOf<Char>()
            var skipLine = false
            for (bracket in line) {
                if (bracket in bracketMatches.keys) {
                    stack.add(bracket)
                } else {
                    if (bracketMatches[stack.last()] != bracket) {
                        lines.remove(line)
                        skipLine = true
                        break
                    } else {
                        stack.removeLast()
                    }
                }
            }
            if (!skipLine) {
                var lineScore = 0L
                for (bracket in stack.reversed()) {
                    lineScore *= 5L
                    lineScore += scoreTable[bracket]!!
                }
                scores.add(lineScore)
            }
        }

        return scores.sorted()[scores.size / 2]
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
