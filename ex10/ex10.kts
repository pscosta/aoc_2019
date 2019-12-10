import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

fun main() = Day10().main(arrayOf("input"))

data class Point(val x: Double = 0.0, val y: Double = 0.0,
                 val isAsteroid: Boolean = false,
                 var angle: Double = 0.0,
                 var baseDistance: Double = 0.0) : Comparable<Point> {

    override fun compareTo(other: Point): Int {
        if (this.angle > other.angle) return 1
        if (this.angle < other.angle) return -1
        if (this.angle == other.angle && this.baseDistance > other.baseDistance) return 1
        if (this.angle == other.angle && this.baseDistance < other.baseDistance) return -1
        else return 0
    }
}

class Day10 {
    fun main(args: Array<String>) {
        val points = args[0].split("\n")
                .mapIndexed { y, l -> l.mapIndexed { x, p -> if ("$p" == "#") Point(x.toDouble(), y.toDouble(), true) else Point() } }
                .flatten()
                .filter { it.isAsteroid }
                .toList()

        val max = points.map { p1 ->
            points.filter { p1 != it }.map { p2 -> angle(p1, p2) }.distinct().count()
        }.max()

        println(max)

        // sry, it's hardcoded :P
        val base = Point(x = 11.0, y = 13.0, isAsteroid = true)

        points.forEach {
            it.baseDistance = distance(base, it)
            it.angle = angle(base, it) - Math.PI / 2
            if (it.angle < 0) it.angle += 10 // hack
        }

        val count = points.filter { base != it }
                .sortedBy { it }
                .groupBy { it.angle }

        println(count[count.keys.toList()[199]])
    }

    fun angle(p1: Point, p2: Point) = atan2((p1.y - p2.y), (p1.x - p2.x)) // 
    fun distance(p1: Point, p2: Point) = sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2)) // euclidean distance
}
