fun main() {
    fun part1(input: List<String>): Int {
        var riskLevel = 0
        val matrix = input.map { line -> line.map { it.digitToInt() } }

        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (isLowPoint(i, j, matrix)) {
                    riskLevel += matrix[i][j] + 1
                }
            }
        }

        return riskLevel
    }

    fun part2(input: List<String>): Int {
        val basinSizes = mutableListOf<Int>()
        val matrix = input.map { line -> line.map { it.digitToInt() }.toMutableList() }.toMutableList()

        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                basinSizes.add(dfs(i, j, matrix))
            }
        }

        return basinSizes.sorted().reversed().subList(0, 3).reduce { acc, i -> acc * i }
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

fun isLowPoint(i: Int, j: Int, matrix: List<List<Int>>): Boolean {
    for (direction in listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))) {
        if (i + direction.first >= 0 &&
            i + direction.first < matrix.size &&
            j + direction.second >= 0 &&
            j + direction.second < matrix[0].size
        ) {
            if (matrix[i][j] >= matrix[i + direction.first][j + direction.second]) {
                return false
            }
        }
    }
    return true
}

fun dfs(i: Int, j: Int, matrix: MutableList<MutableList<Int>>): Int {
    // Out of bounds
    if (i < 0 || i >= matrix.size || j < 0 || j >= matrix[0].size) {
        return 0
    }

    // Already visited or border
    if (matrix[i][j] == 9 || matrix[i][j] == -1) {
        return 0
    }

    matrix[i][j] = -1

    var size = 1
    for (direction in listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))) {
        size += dfs(i + direction.first, j + direction.second, matrix)
    }

    return size
}
