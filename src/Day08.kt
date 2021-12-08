fun main() {
    val trueDigitsMap = hashMapOf(
        Pair(0, "abcefg"),
        Pair(1, "cf"),
        Pair(2, "acdeg"),
        Pair(3, "acdfg"),
        Pair(4, "bcdf"),
        Pair(5, "abdfg"),
        Pair(6, "abdefg"),
        Pair(7, "acf"),
        Pair(8, "abcdefg"),
        Pair(9, "abcdfg"),
    )

    fun part1(input: List<String>): Int {
        val outputs = input.map { it.split("|") }.map { it[1].trim() }.flatMap { it.split(" ") }
        return outputs.count {
            it.length in listOf(
                trueDigitsMap[1]!!.length,
                trueDigitsMap[4]!!.length,
                trueDigitsMap[7]!!.length,
                trueDigitsMap[8]!!.length
            )
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val digitsMap = hashMapOf<Int, String>()
            val lettersMap = hashMapOf<Char, Char>() // true char -> incorrect char
            val (inputs, outputs) = line.split("|").map { it.trim() }.map { it.split(" ") }

            // Fill in the obvious ones
            for (string in inputs) {
                if (string.length == trueDigitsMap[1]!!.length) {
                    digitsMap[1] = string
                }
                if (string.length == trueDigitsMap[4]!!.length) {
                    digitsMap[4] = string
                }
                if (string.length == trueDigitsMap[7]!!.length) {
                    digitsMap[7] = string
                }
                if (string.length == trueDigitsMap[8]!!.length) {
                    digitsMap[8] = string
                }
            }

            // 7 only has one letter (a) that isn't in 1
            lettersMap['a'] = digitsMap[7]!!.filter { !digitsMap[1]!!.contains(it) }.toCharArray()[0]

            // 9 only has one letter (g) that isn't in 4 + 'a'
            digitsMap[9] = inputs.filter { it.length == 6 }.filter { it.containsLetters(digitsMap[4] + lettersMap['a']) }[0]
            lettersMap['g'] = digitsMap[9]!!.filter { !(digitsMap[4] + lettersMap['a']).contains(it) }.toCharArray()[0]

            // 8 only has one letter (e) that isn't in 9
            lettersMap['e'] = digitsMap[8]!!.filter { !digitsMap[9]!!.contains(it) }.toCharArray()[0]

            // 3 is a five-letter digit that contains both of 1's digits
            digitsMap[3] = inputs.filter { it.length == 5 }.filter { it.containsLetters(digitsMap[1]!!) }[0]

            // 'd' is a letter that is left when you take 3's letters - 1's letters - 'a' - 'g'
            lettersMap['d'] = digitsMap[3]!!.filter { !digitsMap[1]!!.contains(it) && it != lettersMap['a'] && it !=  lettersMap['g'] }[0]

            // 0 = letters of 8 subtract 'd'
            val desiredLetters = digitsMap[8]!!.filter { it != lettersMap['d'] }
            digitsMap[0] = inputs.filter { it.length == 6 && !digitsMap.values.contains(it) }.filter { it.containsLetters(desiredLetters) }[0]

            // 6 is the only 6-digit number left
            digitsMap[6] = inputs.filter { it.length == 6 && !digitsMap.values.contains(it) }[0]

            // Of the two remaining 5-digit numbers, the one that contains 'e' is 2 and the one that doesn't is 5
            digitsMap[2] = inputs.filter { it.length == 5 && !digitsMap.values.contains(it) }.filter { it.contains(lettersMap['e']!!) }[0]
            digitsMap[5] = inputs.filter { it.length == 5 && !digitsMap.values.contains(it) }[0]

            // Create the inverse hashmap of digitsMap, so we can map output strings to number values
            val inverseDigitsMap = HashMap<String, Int>()
            for (digit in digitsMap.keys) {
                inverseDigitsMap[digitsMap[digit]!!] = digit
            }

            var decoded = ""
            for (output in outputs) {
                for (str in digitsMap.values) {
                    if (output.length == str.length && str.containsLetters(output)) {
                        decoded += inverseDigitsMap[str]
                    }
                }
            }

            sum += decoded.toInt()
        }

        return sum
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun String.containsLetters(other: String): Boolean {
    for (letter in other) {
        if (!this.contains(letter)) {
            return false
        }
    }
    return true
}
