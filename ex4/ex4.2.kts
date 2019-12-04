fun main(args: Array<String>) {
    val range: List<Int> = args[0].split("-").map { it.toInt() }

    var count = 0
    for (i in range[0]..range[1])
        if (i > 100000 && hasDoubleDigit(i) && isNotDecreasing(i))
            count++

    println(count)
}

fun hasDoubleDigit(number: Int): Boolean {
    for ((j, i) in (1 until "$number".length).withIndex()) {
        if ("$number"[j] == "$number"[i]) return true
    }
    return false
}

fun isNotDecreasing(number: Int): Boolean {
    for (i in 0..("$number".length - 2)) {
        if ("$number"[i] > "$number"[i + 1]) return false
    }
    return true
}

main(arrayOf("153517-630395"))

