package tk.quietdev.level1.utils.ext

fun String.fixJson() : String {
    val deq = ArrayDeque<Boolean>()
    for (c in this) {
        when (c) {
            '{' -> deq.addLast(true)
            '}' -> deq.removeLast()
        }
    }
    return this + "}".repeat(deq.size)
}