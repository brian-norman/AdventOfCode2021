enum class Direction(val value: String) {
    FORWARD("forward"),
    UP("up"),
    DOWN("down")
}

fun main() {
    fun parseInputString(input: String): Pair<Direction, Int> {
        val split = input.split(" ")
        return Pair(Direction.valueOf(split[0].uppercase()), split[1].toInt())
    }

    fun part1(input: List<String>): Int {
        var finalHorizontal = 0
        var finalDepth = 0

        input.map { parseInputString(it) }.map { (direction, distance) ->
            when (direction) {
                Direction.FORWARD -> finalHorizontal += distance
                Direction.UP -> finalDepth -= distance
                Direction.DOWN -> finalDepth += distance
            }
        }

        return finalHorizontal * finalDepth
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var finalHorizontal = 0
        var finalDepth = 0

        input.map { parseInputString(it) }.map { (direction, distance) ->
            when (direction) {
                Direction.FORWARD -> {
                    finalHorizontal += distance
                    finalDepth += aim * distance
                }
                Direction.UP -> {
                    aim -= distance
                }
                Direction.DOWN -> {
                    aim += distance
                }
            }
        }

        return finalHorizontal * finalDepth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
