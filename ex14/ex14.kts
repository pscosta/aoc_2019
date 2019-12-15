
import java.util.stream.Collectors.maxBy
import kotlin.math.ceil

fun main() {
    val input = "input"
    val reactions = HashMap<String, Reaction>()

    input.split("\n").forEach {
        val reaction = Reaction(
                it.split(" => ")[0].split(", ").map { it.split(' ').let { Chemical(it[1], it[0].toInt()) } },
                it.split(" => ")[1].split(' ').let { Chemical(it[1], it[0].toInt()) })
        reactions[reaction.output.name] = reaction
    }

    println(Fuel(reactions).computeFuel(1, "FUEL", 0))

    // grab a chair...
    val maxFuel = (7000000L..9000000L).toList().parallelStream()
            .filter { Fuel(reactions).computeFuel(it, "FUEL", 0) <= 1000000000000 }
            .collect(maxBy(Comparator.naturalOrder()))

    println(maxFuel)
}

data class Chemical(val name: String, val units: Int)
data class Reaction(val chemicals: List<Chemical>, val output: Chemical)

class Fuel(private val reactions: HashMap<String, Reaction>) {
    private val cost = HashMap<String, Long>()

    fun computeFuel(remainingUnits: Long, chemical: String, totalOre: Long): Long {
        if ("ORE" == chemical) return totalOre + remainingUnits

        val reactionUnits = cost[chemical] ?: 0L
        val targetQuantity = (remainingUnits - reactionUnits).toDouble()
        if (targetQuantity == 0.0) {
            cost[chemical] = 0
            return 0
        }

        val reaction = reactions[chemical]!!
        val roundedUnits = ceil(targetQuantity / reaction.output.units).toLong()
        cost[chemical] = reactionUnits - remainingUnits + (roundedUnits * reaction.output.units)

        var acc = totalOre
        reaction.chemicals.forEach { acc += computeFuel(it.units * roundedUnits, it.name, totalOre) }
        return acc
    }
}

