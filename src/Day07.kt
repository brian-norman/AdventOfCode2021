import java.util.Collections.max
import kotlin.math.abs
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        var minFuel = Int.MAX_VALUE
        for (i in 0 .. max(positions)) {
            var fuel = 0
            for (crab in positions) {
                fuel += abs(crab - i)
            }
            minFuel = min(fuel, minFuel)
        }
        return minFuel
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        var minFuel = Int.MAX_VALUE
        for (i in 0 .. max(positions)) {
            var fuel = 0
            for (crab in positions) {
                fuel += (1..abs(crab - i)).sum()
            }
            minFuel = min(fuel, minFuel)
        }
        return minFuel
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

