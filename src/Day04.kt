fun main() {
    fun createBoard(input: List<String>): Board {
        return Board(
            grid = input.map { gridRow ->
                gridRow
                    .split(regex = Regex("\\s+"))
                    .filter { it.isNotBlank() }
                    .map { BoardCell(it.toInt()) }
            }
        )
    }

    fun parseInput(input: List<String>): Pair<List<Int>, List<Board>> {
        val drawnNumbers = input[0].split(",").map { it.toInt() }
        val inputBoards = input.subList(2, input.size)

        val windows = inputBoards.windowed(5, 6)
        val boards = windows.map { createBoard(it) }

        return Pair(drawnNumbers, boards)
    }

    fun part1(input: List<String>): Int {
        var (drawnNumbers: List<Int>, boards: List<Board>) = parseInput(input)

        for (number in drawnNumbers) {
            boards = boards.map { board -> board.mark(number) }
            val bingoBoards = boards.filter { board -> board.hasBingo() }
            if (bingoBoards.isNotEmpty()) {
                return bingoBoards[0].calculateScore(number)
            }
        }

        return input.size
    }

    fun part2(input: List<String>): Int {
        var (drawnNumbers: List<Int>, boards: List<Board>) = parseInput(input)

        for (number in drawnNumbers) {
            boards = boards.map { board -> board.mark(number) }
            val bingoBoards = boards.filter { board -> board.hasBingo() }
            if (bingoBoards.size == 1 && boards.size == 1) {
                return bingoBoards[0].calculateScore(number)
            }
            boards = boards.filter { !bingoBoards.contains(it) }
        }

        return input.size
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}


data class Board(
    val grid: List<List<BoardCell>>
) {
    override fun toString(): String {
        var ret = ""
        for (row in grid) {
            ret += row.map { cell ->
                if (cell.marked) {
                    "'${cell.value}'"
                } else {
                    "${cell.value}"
                }
            }
            ret += "\n"
        }
        return ret
    }

    fun hasBingo(): Boolean {
        // Check every row
        for (row in grid) {
            if (row.all { it.marked }) {
                return true
            }
        }

        // Check every column
        for (column in 0 until grid[0].size) {
            var markedCount = 0
            for (row in grid) {
                if (row[column].marked) {
                    markedCount ++
                }
            }
            if (markedCount == grid[0].size) {
                return true
            }
        }

        return false
    }

    fun calculateScore(lastCalledNumber: Int): Int {
        val unmarkedSum = grid.sumOf { row -> row.filter { !it.marked }.sumOf { cell -> cell.value } }
        return lastCalledNumber * unmarkedSum
    }
}

fun Board.mark(number: Int): Board {
    return this.copy(
        grid = grid.map { boardCells -> boardCells.map { boardCell -> boardCell.mark(number) } }
    )
}

data class BoardCell(
    val value: Int,
    val marked: Boolean = false
)

fun BoardCell.mark(number: Int): BoardCell {
    return if (value == number) {
        this.copy(marked = true)
    } else {
        this.copy()
    }
}
