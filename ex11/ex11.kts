import IntCode.Direction.Companion.nextDirection
import IntCode.Direction.Companion.nextPosition
import IntCode.Direction.UP
import IntCode.OP.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch

suspend fun main() = IntCode().main(arrayOf("input"))

class IntCode {
    suspend fun main(args: Array<String>) {
        val mem = HashMap<Long, Long>()
        args[0].split(",").forEachIndexed { i, value -> mem[i.toLong()] = value.toLong() }

        println(paint(mem, HashMap(), 0).size)

        val painting = paint(mem, HashMap(), 1)
        val minY = painting.values.map { it.y }.min()!!
        val maxY = painting.values.map { it.y }.max()!!
        val minX = painting.values.map { it.x }.min()!!
        val maxX = painting.values.map { it.x }.max()!!

        println((minY..maxY).reversed().joinToString("\n") { y ->
            (minX..maxX).joinToString("") { x -> painting["$x,$y"]?.printColor ?: " " }
        })
    }

    suspend fun paint(mem: HashMap<Long, Long>, panel: HashMap<String, Point>, input: Int): HashMap<String, Point> {
        var output = 0
        var currentDir = UP
        var currentPos = Point(0, 0)
        val inCh = Channel<Int>(UNLIMITED).input(input)
        val outCh = Channel<Int>(UNLIMITED)

        val paintJob = GlobalScope.launch { compute(mem, inCh, outCh) }

        GlobalScope.launch {
            while (true) {
                val value = outCh.receive()
                output++
                when {
                    output % 2 == 0 -> {
                        currentDir = nextDirection(currentDir, value)
                        currentPos = moveToNextPosition(nextPosition(currentDir, currentPos), panel)
                        inCh.send(currentPos.color)
                    }
                    else -> {
                        currentPos.color = value
                        currentPos.timesPainted += 1
                        panel[currentPos.key] = currentPos
                    }
                }
            }
        }
        paintJob.join()
        return panel
    }

    fun moveToNextPosition(next: Point, panel: HashMap<String, Point>) =
            if (next.key !in panel) {
                panel[next.key] = next
                next
            } else panel[next.key]!!

    enum class Direction {
        UP, DOWN, RIGHT, LEFT;

        companion object {
            fun nextDirection(current: Direction, code: Int): Direction = when (current) {
                UP -> if (code == 0) LEFT else RIGHT
                DOWN -> if (code == 0) RIGHT else LEFT
                RIGHT -> if (code == 0) UP else DOWN
                LEFT -> if (code == 0) DOWN else UP
            }

            fun nextPosition(current: Direction, pos: Point): Point = when (current) {
                UP -> Point(pos.x, pos.y + 1)
                DOWN -> Point(pos.x, pos.y - 1)
                RIGHT -> Point(pos.x + 1, pos.y)
                LEFT -> Point(pos.x - 1, pos.y)
            }
        }
    }

    data class Point(val x: Int, val y: Int) {
        var color: Int = 0
        var timesPainted = 0
        val key: String = "$x,$y"
        val printColor: String get() = if (color == 1) "X" else " "
    }

    data class Param(val value: Long, val mode: Char)
    data class Instruction(val opcode: OP, val step: Int, val params: List<Param>)
    enum class OP(val code: String, val step: Int) {
        INC("01", 4),
        MUL("02", 4),
        READ("03", 2),
        WRITE("04", 2),
        JMP_T("05", 3),
        JMP_F("06", 3),
        EQ("08", 4),
        LESS("07", 4),
        BASE("09", 2),
        HALT("99", 0);

        companion object {
            private val map = values().associateBy(OP::code)
            fun toOpCode(code: String): OP = map[code]!!
        }
    }

    infix fun HashMap<Long, Long>.at(key: Long) = if (key in this) this[key]!! else {
        this[key] = 0L; 0L
    }

    suspend infix fun Channel<Int>.input(obj: Int): Channel<Int> {
        this.send(obj); return this
    }

    fun loadInstruction(pc: Long, mem: HashMap<Long, Long>): Instruction {
        val op = completeOp("${mem[pc]}")
        val opcode = OP.toOpCode("${op[3]}${op[4]}")

        val params = when (opcode) {
            INC, MUL, LESS, EQ -> listOf(Param(mem at pc + 1, op[2]), Param(mem at pc + 2, op[1]), Param(mem at pc + 3, op[0]))
            READ, WRITE, BASE -> listOf(Param(mem at pc + 1, op[2]))
            JMP_T, JMP_F -> listOf(Param(mem at pc + 1, op[2]), Param(mem at pc + 2, op[1]))
            else -> emptyList()
        }
        return Instruction(opcode, opcode.step, params)
    }

    fun completeOp(s: String): String {
        var op = s
        for (i in op.length..4) op = "0$op"
        return op
    }

    fun loadParam(idx: Int, inst: Instruction, mem: HashMap<Long, Long>, base: Long): Long =
            when (inst.params[idx].mode) {
                '0' -> mem at (inst.params[idx].value)
                '2' -> mem at (base + inst.params[idx].value)
                else -> inst.params[idx].value
            }

    fun loadWriteAddr(param: Param, base: Long, p: Long) = if (param.mode == '2') base + p else p

    suspend fun compute(mem: HashMap<Long, Long>, input: Channel<Int>, output: Channel<Int>) {
        var pc = 0L
        var base = 0L

        cycle@ while (true) {
            val inst = loadInstruction(pc, mem)
            pc += inst.step

            when (inst.opcode) {
                INC -> {
                    val p1 = loadParam(0, inst, mem, base)
                    val p2 = loadParam(1, inst, mem, base)
                    val p3 = inst.params[2].value
                    mem[loadWriteAddr(inst.params[2], base, p3)] = p1 + p2
                }
                MUL -> {
                    val p1 = loadParam(0, inst, mem, base)
                    val p2 = loadParam(1, inst, mem, base)
                    val p3 = inst.params[2].value
                    mem[loadWriteAddr(inst.params[2], base, p3)] = p1 * p2
                }
                JMP_T -> {
                    val p1 = loadParam(0, inst, mem, base)
                    val p2 = loadParam(1, inst, mem, base)
                    if (p1 != 0L) pc = p2
                }
                JMP_F -> {
                    val p1 = loadParam(0, inst, mem, base)
                    val p2 = loadParam(1, inst, mem, base)
                    if (p1 == 0L) pc = p2
                }
                LESS -> {
                    val p1 = loadParam(0, inst, mem, base)
                    val p2 = loadParam(1, inst, mem, base)
                    val p3 = inst.params[2].value
                    val dst = loadWriteAddr(inst.params[2], base, p3)
                    if (p1 < p2) mem[dst] = 1 else mem[dst] = 0
                }
                EQ -> {
                    val p1 = loadParam(0, inst, mem, base)
                    val p2 = loadParam(1, inst, mem, base)
                    val p3 = inst.params[2].value
                    val dst = loadWriteAddr(inst.params[2], base, p3)
                    if (p1 == p2) mem[dst] = 1 else mem[dst] = 0
                }
                READ -> {
                    val p1 = inst.params[0].value
                    mem[loadWriteAddr(inst.params[0], base, p1)] = input.receive().toLong()
                }
                WRITE -> output.send(loadParam(0, inst, mem, base).toInt())
                BASE -> base += loadParam(0, inst, mem, base)
                HALT -> break@cycle
            }
        }
    }
}
