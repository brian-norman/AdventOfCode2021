fun main() {
    fun part1(input: List<String>): Int {
        val days = 80
        val fishes: MutableList<Int> = input[0].split(",").map { it.toInt() }.toMutableList()

        for (i in 0 until days) {
            for (index in 0 until fishes.size) {
                if (fishes[index] == 0) {
                    fishes.add(8)
                    fishes[index] = 6
                } else {
                    fishes[index] -= 1
                }
            }
        }

        return fishes.size
    }

    fun part2(input: List<String>): Long {
        val days = 256
        val fishMap = HashMap<Int, Long>()

        val fishes: List<Int> = input[0].split(",").map { it.toInt() }
        for (fish in fishes) {
            fishMap[fish] = fishMap[fish]?.plus(1) ?: 1
        }

        fishMap[6] = 0
        fishMap[8] = 0
        fishMap[0] = 0

        println("initial: $fishMap")

        for (i in 0 until days) {
            var babies = 0L
            for (key in fishMap.keys.toList()) {
                if (key == 0) {
                    babies = fishMap[0]!!
                    fishMap[0] = 0
                } else {
                    fishMap[key-1] = fishMap[key]!!
                    fishMap[key] = 0
                }
            }
            fishMap[8] = fishMap[8]!! + babies
            fishMap[6] = fishMap[6]!! + babies
        }

        println("final: $fishMap")
        return fishMap.values.sum()
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
