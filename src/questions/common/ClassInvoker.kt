package questions.common

import _utils.SkipDocumentation
import utils.shouldBe

@SkipDocumentation
class ClassInvoker<T, A>(private val methodNames: List<String>, val argsExtractor: (T) -> A?) {
    fun invoke(args: List<T>, expectedAnswers: List<Any?>? = null) {
        require(methodNames.size == args.size)
        require(methodNames.isNotEmpty())
        val klassName = methodNames.first()
        val klass = Class.forName("questions.$klassName")
        val klassInstance = klass.getConstructor().newInstance()
        for (i in 1..methodNames.lastIndex) {
            val method = klass.methods.find { it.name == methodNames[i] }!!
            val argument = argsExtractor(args[i])
            if (argument == null) {
                val invoke = method.invoke(klassInstance)
                if (expectedAnswers != null) {
                    println("position $i $klassName#${method.name}()=$invoke; expected=${expectedAnswers[i]}")
                    invoke shouldBe expectedAnswers[i]
                } else {
                    println("position $i $klassName#${method.name}()=$invoke")
                }
            } else {
                val invoke = method.invoke(klassInstance, argument)
                if (expectedAnswers != null) {
                    println("position $i $klassName#${method.name}($argument)=$invoke; expected=${expectedAnswers[i]}")
                    invoke shouldBe expectedAnswers[i]
                } else {
                    println("position $i $klassName#${method.name}()=$invoke")
                }
            }
        }
    }
}

fun main() {
    val classInvoker = ClassInvoker<IntArray, Int>(
        listOf(
            "RandomizedSet",
            "insert",
            "insert",
            "remove",
            "getRandom",
            "remove",
            "insert",
            "getRandom"
        )
    ) {
        it.getOrNull(0)
    }
    classInvoker.invoke(
        listOf(
            intArrayOf(),
            intArrayOf(1),
            intArrayOf(2),
            intArrayOf(2),
            intArrayOf(),
            intArrayOf(1),
            intArrayOf(4),
            intArrayOf(),
        ),
        listOf(null, true, true, true, 1, true, true, 4)
    )
}