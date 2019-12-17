import kotlin.math.abs

fun main() {
    val input = "input".map { "$it".toInt() }.toList()
    val messageOffset = "5975053".toInt()

    val p1Output = computeRecursive(input)
    println(p1Output.joinToString("").substring(0, 8))

    val p2Output = compute(messageOffset, input)
    println(p2Output.take(8).joinToString(""))
}

private fun compute(messageOffset: Int, input: List<Int>): IntArray {
    val p2Output = (messageOffset until 10000 * input.size)
            .map { input[it % input.size] }
            .toIntArray()

    (0 until 100).forEach {
        p2Output.indices.reversed().fold(0) { total, ids ->
            (abs(total + p2Output[ids]) % 10).apply { p2Output[ids] = this }
        }
    }
    return p2Output
}

tailrec fun computeRecursive(input: List<Int>, acc: Int = 0): List<Int> {
    return if (acc == 100) input else computeRecursive(compute(input), acc + 1)
}

private fun compute(input: List<Int>): List<Int> {
    var total = ""
    for (iter in 1..input.size) {
        var acc = 0
        for (pos in input.indices) {
            val factor = next(pos, iter, 1)
            acc += (input[pos] * factor)
        }
        val onesDigit = "$acc"["$acc".length - 1]
        total = "$total$onesDigit"
    }
    return total.map { "$it".toInt() }.toList()
}

val pattern = arrayOf(0, 1, 0, -1)
fun next(position: Int, iteration: Int, offset: Int) = pattern[((position + offset) / iteration) % 4]
