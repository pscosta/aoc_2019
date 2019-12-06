fun main(args: Array<String>) {
    val input = args[0].split(",").map { it.toInt() }.toList()
    compute(input.toMutableList(), 5)
}

data class Param(val value: Int, val mode: Char)
data class Instruction(val opcode: OP, val step: Int, val params: List<Param>)
enum class OP(val code: String, val step: Int, val params: Int) {
    INC("01", 4, 3),
    MUL("02", 4, 3),
    READ("03", 2, 1),
    WRITE("04", 2, 1),
    JMP_T("05", 3, 2),
    JMP_F("06", 3, 2),
    LESS("07", 4, 3),
    EQ("08", 4, 3),
    HALT("99", 0, 0);

    companion object {
        private val map = values().associateBy(OP::code)
        fun fromCode(code: String): OP = map[code]!!
    }
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

fun compute(input: MutableList<Int>, i1: Int) {
    var pc = 0
    cycle@ while (true) {
        val inst = loadInstruction(input.subList(pc, (input.size - 1)))
        pc += inst.step
        when (inst.opcode) {
            OP.INC -> {
                val p1 = loadParam(0, inst, input)
                val p2 = loadParam(1, inst, input)
                val p3 = inst.params[2].value
                input[p3] = p1 + p2
            }
            OP.MUL -> {
                val p1 = loadParam(0, inst, input)
                val p2 = loadParam(1, inst, input)
                val p3 = inst.params[2].value
                input[p3] = p1 * p2
            }
            OP.JMP_T -> {
                val p1 = loadParam(0, inst, input)
                val p2 = loadParam(1, inst, input)
                if (p1 != 0) pc = p2
            }
            OP.JMP_F -> {
                val p1 = loadParam(0, inst, input)
                val p2 = loadParam(1, inst, input)
                if (p1 == 0) pc = p2
            }
            OP.LESS -> {
                val p1 = loadParam(0, inst, input)
                val p2 = loadParam(1, inst, input)
                val p3 = inst.params[2].value
                if (p1 < p2) input[p3] = 1 else input[p3] = 0
            }
            OP.EQ -> {
                val p1 = loadParam(0, inst, input)
                val p2 = loadParam(1, inst, input)
                val p3 = inst.params[2].value
                if (p1 == p2) input[p3] = 1 else input[p3] = 0
            }
            OP.READ -> input[inst.params[0].value] = i1
            OP.WRITE -> println(loadParam(0, inst, input))
            OP.HALT -> break@cycle
        }
    }
}

val input = arrayOf("3,225,1,225,6,6,1100,1,238,225,104,0,1101,81,30,225,1102,9,63,225,1001,92,45,224,101,-83,224,224,4,224,102,8,223,223,101,2,224,224,1,224,223,223,1102,41,38,225,1002,165,73,224,101,-2920,224,224,4,224,102,8,223,223,101,4,224,224,1,223,224,223,1101,18,14,224,1001,224,-32,224,4,224,1002,223,8,223,101,3,224,224,1,224,223,223,1101,67,38,225,1102,54,62,224,1001,224,-3348,224,4,224,1002,223,8,223,1001,224,1,224,1,224,223,223,1,161,169,224,101,-62,224,224,4,224,1002,223,8,223,101,1,224,224,1,223,224,223,2,14,18,224,1001,224,-1890,224,4,224,1002,223,8,223,101,3,224,224,1,223,224,223,1101,20,25,225,1102,40,11,225,1102,42,58,225,101,76,217,224,101,-153,224,224,4,224,102,8,223,223,1001,224,5,224,1,224,223,223,102,11,43,224,1001,224,-451,224,4,224,1002,223,8,223,101,6,224,224,1,223,224,223,1102,77,23,225,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,8,226,677,224,1002,223,2,223,1006,224,329,1001,223,1,223,7,226,226,224,102,2,223,223,1006,224,344,101,1,223,223,108,677,677,224,1002,223,2,223,1006,224,359,101,1,223,223,1107,226,677,224,1002,223,2,223,1005,224,374,101,1,223,223,1008,677,226,224,1002,223,2,223,1005,224,389,101,1,223,223,1007,677,226,224,1002,223,2,223,1005,224,404,1001,223,1,223,1107,677,226,224,1002,223,2,223,1005,224,419,1001,223,1,223,108,677,226,224,102,2,223,223,1006,224,434,1001,223,1,223,7,226,677,224,102,2,223,223,1005,224,449,1001,223,1,223,107,226,226,224,102,2,223,223,1006,224,464,101,1,223,223,107,677,226,224,102,2,223,223,1006,224,479,101,1,223,223,1007,677,677,224,1002,223,2,223,1006,224,494,1001,223,1,223,1008,226,226,224,1002,223,2,223,1006,224,509,101,1,223,223,7,677,226,224,1002,223,2,223,1006,224,524,1001,223,1,223,1007,226,226,224,102,2,223,223,1006,224,539,101,1,223,223,8,677,226,224,1002,223,2,223,1006,224,554,101,1,223,223,1008,677,677,224,102,2,223,223,1006,224,569,101,1,223,223,1108,677,226,224,102,2,223,223,1005,224,584,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,599,1001,223,1,223,1108,677,677,224,1002,223,2,223,1006,224,614,1001,223,1,223,1107,677,677,224,1002,223,2,223,1005,224,629,1001,223,1,223,108,226,226,224,1002,223,2,223,1005,224,644,101,1,223,223,8,226,226,224,1002,223,2,223,1005,224,659,101,1,223,223,1108,226,677,224,1002,223,2,223,1006,224,674,101,1,223,223,4,223,99,226")
main(input)

