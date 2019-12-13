import kotlin.math.abs

fun main() = Newton().main(arrayOf("input"))

class Newton {
    fun main(args: Array<String>) {
        val moons = listOf(Moon(12, 0, -15), Moon(-8, -5, -10), Moon(7, -17, 1), Moon(2, -11, -6))

        (0..999).map {
            applyGravity(moons.toMutableList())
            applyVelocity(moons)
        }

        println(moons.map { it.totalEnergy }.sum())
    }

    fun applyGravity(moons: List<Moon>) {
        for (i in moons.indices)
            for (j in i until moons.size) {
                val m1 = moons[i]
                val m2 = moons[j]

                if (m1.x > m2.x) {
                    m1.velocity.x -= 1L
                    m2.velocity.x += 1L
                } else if (m1.x < m2.x) {
                    m1.velocity.x += 1L
                    m2.velocity.x -= 1L
                }
                if (m1.y > m2.y) {
                    m1.velocity.y -= 1L
                    m2.velocity.y += 1L
                } else if (m1.y < m2.y) {
                    m1.velocity.y += 1L
                    m2.velocity.y -= 1L
                }
                if (m1.z > m2.z) {
                    m1.velocity.z -= 1L
                    m2.velocity.z += 1L
                } else if (m1.z < m2.z) {
                    m1.velocity.z += 1L
                    m2.velocity.z -= 1L
                }
            }
    }

    fun applyVelocity(moons: List<Moon>) = moons.forEach {
        it.x += it.velocity.x
        it.y += it.velocity.y
        it.z += it.velocity.z
    }

    data class Moon(var x: Long, var y: Long, var z: Long, var velocity: Velocity = Velocity(0L, 0L, 0L)) {
        val potentialEnergy: Long get() = abs(x) + abs(y) + abs(z)
        val kineticEnergy: Long get() = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)
        val totalEnergy: Long get() = potentialEnergy * kineticEnergy
    }

    data class Velocity(var x: Long, var y: Long, var z: Long)
}

