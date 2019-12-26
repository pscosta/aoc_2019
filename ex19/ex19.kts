import IntCode.OP.*
import kotlinx.coroutines.channels.Channel
import kotlin.collections.mutableListOf as list

fun main() {
    val mem = HashMap<Long, Long>()
    "input".split(",").forEachIndexed { i, value -> mem[i.toLong()] = value.toLong() }

    val intCode = IntCode(mem)
    var count = 0
    for (y in 0L..49L)
        for (x in 0L..49L)
            if (intCode.compute(list(x, y)) == 1L) count += 1
    
    println("Part1: $count")

    mainloop@ for (y in 500L..1000L)
        for (x in 500L..1000L) {
            if (intCode.compute(list(x, y)) != 1L) continue
            val p1 = intCode.compute(list(x + 99L, y))
            val p2 = intCode.compute(list(x, y + 99L))
            if (p1 == 1L && p2 == 1L) {
                println("Part 2: ($x, $y) -> ${x * 10000 + y}")
                break@mainloop
            }
        }
}

class IntCode(val inMem: HashMap<Long, Long>) {
    var mem = HashMap(inMem)
    var pc: Long = 0
    var base: Long = 0

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

    suspend infix fun Channel<Long>.input(obj: Long): Channel<Long> {
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

    fun compute(input: MutableList<Long>): Long {
        var inst: Instruction
        mem = HashMap(inMem)
        pc = 0
        base = 0
        cycle@ while (true) {
            inst = loadInstruction(pc, mem)
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
                    mem[loadWriteAddr(inst.params[0], base, p1)] = input[0]
                    input.removeAt(0)
                }
                WRITE -> {
                    return loadParam(0, inst, mem, base)
                }
                BASE -> base += loadParam(0, inst, mem, base)
                HALT -> break@cycle
            }
        }
        return -1L
    }
}
