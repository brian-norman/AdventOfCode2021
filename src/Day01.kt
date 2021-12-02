fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        val inputAsNumbers = input.map { it.toInt() }
        for (n in 1 until inputAsNumbers.size) {
            if (inputAsNumbers[n] > inputAsNumbers[n-1]) {
                count++
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        val inputAsNumbers = input.map { it.toInt() }
        for (n in 2 until inputAsNumbers.size-1) {
            val windowSum = inputAsNumbers[n-1] + inputAsNumbers[n] + inputAsNumbers[n+1]
            val prevWindowSum = inputAsNumbers[n-2] + inputAsNumbers[n-1] + inputAsNumbers[n]
            if (windowSum > prevWindowSum) {
                count++
            }
        }
        return count
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
