import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val filled = HashMap<Coordinate, Int>()

        input.flatMap { getCoordinatesOfLine(it) }.map { coordinate ->
            filled[coordinate] = filled[coordinate]?.plus(1) ?: 1
        }

        return filled.count { it.value >= 2 }
    }

    fun part2(input: List<String>): Int {
        val filled = HashMap<Coordinate, Int>()

        input.flatMap { getCoordinatesOfLine(it, diagonals = true) }.map { coordinate ->
            filled[coordinate] = filled[coordinate]?.plus(1) ?: 1
        }

        return filled.count { it.value >= 2 }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

/**
 * Gets all vertical/horizontal coordinates that lie between two points to make a line
 */
fun getCoordinatesOfLine(input: String, diagonals: Boolean = false): Set<Coordinate> {
    val (firstCoordinate, secondCoordinate) = getCoordinatesOfInput(input)
    val coordinates = mutableSetOf(firstCoordinate, secondCoordinate)

    if (firstCoordinate.y == secondCoordinate.y) {
        for (i in min(firstCoordinate.x, secondCoordinate.x) .. max(firstCoordinate.x, secondCoordinate.x)) {
            coordinates.add(Coordinate(i, firstCoordinate.y))
        }
    } else if (firstCoordinate.x == secondCoordinate.x) {
        for (i in min(firstCoordinate.y, secondCoordinate.y) .. max(firstCoordinate.y, secondCoordinate.y)) {
            coordinates.add(Coordinate(firstCoordinate.x, i))
        }
    } else {
        if (diagonals) {
            val stepsApart = abs(firstCoordinate.x - secondCoordinate.x)
            val (horizontalIncrement, verticalIncrement) = getDirectionIncrements(firstCoordinate, secondCoordinate)
            for (i in 1 until stepsApart) {
                val coord = Coordinate(firstCoordinate.x + horizontalIncrement*i, firstCoordinate.y + verticalIncrement*i)
                coordinates.add(coord)
            }

        } else {
            // Diagonal lines ignored
            coordinates.removeAll(coordinates)
        }
    }

    return coordinates
}

fun getDirectionIncrements(first: Coordinate, second: Coordinate): Pair<Int, Int> {
    val horizontal: Int = if (first.x < second.x) {
        1
    } else {
        -1
    }

    val vertical: Int = if (first.y < second.y) {
        1
    } else {
        -1
    }
    return Pair(horizontal, vertical)
}

fun getCoordinatesOfInput(input: String): Pair<Coordinate, Coordinate> {
    val (firstCoordinateInput, secondCoordinateInput) = input.split(" -> ")
    val firstCoordinate = firstCoordinateInput.split(",").map { it.toInt() }.toCoordinate()
    val secondCoordinate = secondCoordinateInput.split(",").map { it.toInt() }.toCoordinate()

    return Pair(firstCoordinate, secondCoordinate)
}

fun List<Int>.toCoordinate(): Coordinate {
    return Coordinate(this[0], this[1])
}

data class Coordinate(
    val x: Int,
    val y: Int
)
