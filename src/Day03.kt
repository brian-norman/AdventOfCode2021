fun main() {
    fun part1(input: List<String>): Int {
        var gammaBinaryString = ""
        var epsilonBinaryString = ""

        for (i in 0 until input[0].length) {
            val zeros = input.map { it[i] }.count { bit -> bit == '0' }
            val ones = input.size - zeros

            if (zeros > ones) {
                gammaBinaryString += '0'
                epsilonBinaryString += '1'
            } else {
                gammaBinaryString += '1'
                epsilonBinaryString += '0'
            }
        }

        return multiplyTwoBinaryStrings(first = gammaBinaryString, second = epsilonBinaryString)
    }

    fun part2(input: List<String>): Int {
        // Oxygen Generator Rating
        var oxygenTempInputs = input
        for (i in 0 until oxygenTempInputs[0].length) {
            if (oxygenTempInputs.size == 1) {
                break
            }

            val zeros = oxygenTempInputs.map { it[i] }.count { bit -> bit == '0' }
            val ones = oxygenTempInputs.size - zeros

            oxygenTempInputs = if (zeros > ones) {
                oxygenTempInputs.filter { it[i] == '0' }
            } else {
                oxygenTempInputs.filter { it[i] == '1' }
            }
        }
        val oxygenBinaryString = oxygenTempInputs[0]

        // CO2 Scrubber Rating
        var scrubberTempInputs = input
        for (i in 0 until scrubberTempInputs[0].length) {
            if (scrubberTempInputs.size == 1) {
                break
            }

            val zeros = scrubberTempInputs.map { it[i] }.count { bit -> bit == '0' }
            val ones = scrubberTempInputs.size - zeros

            scrubberTempInputs = if (zeros <= ones) {
                scrubberTempInputs.filter { it[i] == '0' }
            } else {
                scrubberTempInputs.filter { it[i] == '1' }
            }
        }
        val scrubberBinaryString = scrubberTempInputs[0]

        return multiplyTwoBinaryStrings(oxygenBinaryString, scrubberBinaryString)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}


fun multiplyTwoBinaryStrings(first: String, second: String): Int {
    if (first.isEmpty() || second.isEmpty()) {
        return 0
    }
    return first.toInt(2) * second.toInt(2)
}
