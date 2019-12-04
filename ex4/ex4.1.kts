fun main(args: Array<String>) {
    val range: List<Int> = args[0].split("-").map { it.toInt() }

    var count = 0
    for (i in range[0]..range[1])
        if (i > 100000 && hasExactlyDoubleDigit(i) && isNotDecreasing(i))
            count++

    println(count)
}

fun hasExactlyDoubleDigit(number: Int): Boolean {
    return "$number".groupBy { it }.map { it.value.size }.contains(2)
}

fun isNotDecreasing(number: Int): Boolean {
    for (i in 0..("$number".length - 2)) {
        if ("$number"[i] > "$number"[i + 1]) return false
    }
    return true
}

main(arrayOf("153517-630395"))

