fun main() = ImageDecoder().main(arrayOf("input"))

data class Layer(val data: CharSequence, val count: Map<String, List<Char>>)

class ImageDecoder {
    fun main(args: Array<String>) {
        val imageSize = 150
        val width = 25

        val layers = args[0].chunked(imageSize)
                .map { Layer(it, it.groupBy { "$it" }) }

        layers.minBy { it.count["0"]?.size ?: 0 }
                .also { println(it!!.count["1"]!!.size * it.count["2"]!!.size) }

        (0 until imageSize)
                .map { i -> layers.first { "${it.data[i]}" != "2" }.data[i] }
                .joinToString(separator = "")
                .chunked(width)
                .map { it.map { char -> if (char == '0') print(" ") else print("1") }.also { println("") } }
    }
}
