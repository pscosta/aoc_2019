fun main(args: Array<String>) {
    val input = args[0].split(",").map { it.split(")") }.map { it[1] to it[0] }.toMap()

    val checksum = checksum(input)
    println("$checksum")

    val from = input["SAN"]!!
    val dest = input["YOU"]!!
    val shortestPath = pathToRoot(input, from, pathToRoot(input, dest, HashSet()))
    println(shortestPath.size)
}

fun checksum(input: Map<String, String>): Int {
    var count = 0
    input.forEach {
        var sat = it.value
        while (true) {
            count++
            val upper = input[sat]
            sat = upper ?: break
        }
    }
    return count
}

fun pathToRoot(input: Map<String, String>, from: String, path: HashSet<String>): HashSet<String> {
    var next = from
    while (true) {
        if (path.contains(next)) path.remove(next)
        else path.add(next)
        next = input[next] ?: break
    }
    return path
}

main(arrayOf("input"))
