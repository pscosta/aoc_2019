import com.google.common.collect.Collections2
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

suspend fun main() = Prog().main(arrayOf("input"))

class Prog {
    suspend fun main(args: Array<String>) {
        val input = args[0].split(",").map { it.toInt() }.toList()

        Collections2.permutations(listOf(5, 6, 7, 8, 9))
                .map { execute(it, input) }
                .max()
                .also { println(it) }
    }

    suspend fun execute(phases: MutableList<Int>, input: List<Int>): Int {
        val ch1 = Channel<Int>(Int.MAX_VALUE).input(phases[0]).input(0)
        val ch2 = Channel<Int>(Int.MAX_VALUE).input(phases[1])
        val ch3 = Channel<Int>(Int.MAX_VALUE).input(phases[2])
        val ch4 = Channel<Int>(Int.MAX_VALUE).input(phases[3])
        val ch5 = Channel<Int>(Int.MAX_VALUE).input(phases[4])

        var output: Int? = 0
        launch { compute(input.toMutableList(), ch1, ch2) }
        launch { compute(input.toMutableList(), ch2, ch3) }
        launch { compute(input.toMutableList(), ch3, ch4) }
        launch { compute(input.toMutableList(), ch4, ch5) }
        launch { output = compute(input.toMutableList(), ch5, ch1) }.join()
        return output!!
    }

    data class Param(val value: Int, val mode: Char)
    data class Instruction(val opcode: OP, val step: Int, val params: List<Param>)
    enum class OP(val code: String, val step: Int) {
        INC("01", 4),
        MUL("02", 4),
        READ("03", 2),
        WRITE("04", 2),
        JMP_T("05", 3),
        JMP_F("06", 3),
        LESS("07", 4),
        EQ("08", 4),
        HALT("99", 0);

        companion object {
            private val map = values().associateBy(OP::code)
            fun fromCode(code: String): OP = map[code]!!
        }
    }

    suspend infix fun Channel<Int>.input(obj: Int): Channel<Int> {
        this.send(obj); return this
    }

    fun loadInstruction(i: List<Int>): Instruction {
        val inst = completeOp("${i[0]}")
        val op = OP.fromCode("${inst[3]}${inst[4]}")

        val params = when (op) {
            OP.INC, OP.MUL, OP.LESS, OP.EQ -> listOf(Param(i[1], inst[2]), Param(i[2], inst[1]), Param(i[3], inst[0]))
            OP.READ, OP.WRITE -> listOf(Param(i[1], inst[2]))
            OP.JMP_T, OP.JMP_F -> listOf(Param(i[1], inst[2]), Param(i[2], inst[1]))
            else -> emptyList()
        }
        return Instruction(op, op.step, params)
    }

    fun completeOp(s: String): String {
        var op = s
        for (i in op.length..4) op = "0$op"
        return op
    }

    fun loadParam(idx: Int, inst: Instruction, input: MutableList<Int>) =
            if (inst.params[idx].mode == '0') input[inst.params[idx].value]
            else inst.params[idx].value

    suspend fun compute(prog: MutableList<Int>, input: Channel<Int>, output: Channel<Int>): Int? {
        var pc = 0
        var out: Int? = null

        cycle@ while (true) {
            val inst = loadInstruction(prog.subList(pc, (prog.size - 1)))
            pc += inst.step
            when (inst.opcode) {
                OP.INC -> {
                    val p1 = loadParam(0, inst, prog)
                    val p2 = loadParam(1, inst, prog)
                    val p3 = inst.params[2].value
                    prog[p3] = p1 + p2
                }
                OP.MUL -> {
                    val p1 = loadParam(0, inst, prog)
                    val p2 = loadParam(1, inst, prog)
                    val p3 = inst.params[2].value
                    prog[p3] = p1 * p2
                }
                OP.JMP_T -> {
                    val p1 = loadParam(0, inst, prog)
                    val p2 = loadParam(1, inst, prog)
                    if (p1 != 0) pc = p2
                }
                OP.JMP_F -> {
                    val p1 = loadParam(0, inst, prog)
                    val p2 = loadParam(1, inst, prog)
                    if (p1 == 0) pc = p2
                }
                OP.LESS -> {
                    val p1 = loadParam(0, inst, prog)
                    val p2 = loadParam(1, inst, prog)
                    val p3 = inst.params[2].value
                    if (p1 < p2) prog[p3] = 1 else prog[p3] = 0
                }
                OP.EQ -> {
                    val p1 = loadParam(0, inst, prog)
                    val p2 = loadParam(1, inst, prog)
                    val p3 = inst.params[2].value
                    if (p1 == p2) prog[p3] = 1 else prog[p3] = 0
                }
                OP.READ -> prog[inst.params[0].value] = input.receive()
                OP.WRITE -> {
                    output.send(loadParam(0, inst, prog))
                    out = loadParam(0, inst, prog)
                }
                OP.HALT -> break@cycle
            }
        }
        return out
    }
}
