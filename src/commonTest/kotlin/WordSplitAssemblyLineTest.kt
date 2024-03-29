package dev.nies.craft

import kotlin.test.Test
import kotlin.test.assertEquals

class WordSplitAssemblyLineTest {
    val testString = """
        1@2@3
        4#5#6
        7&8&9
    """

    @Test fun `Word is split by multiple recipes`() {
        val splitAssemblyLine = AssemblyLine<MutableList<String>>()
        splitAssemblyLine.install(StringSplitProcessor("\n"))
        splitAssemblyLine.install(StringSplitProcessor("@"))
        splitAssemblyLine.install(StringSplitProcessor("#"))
        splitAssemblyLine.install(StringSplitProcessor("&"))

        val stringContainer = mutableListOf(testString)
        splitAssemblyLine.process(stringContainer)

        assertEquals(
            mutableListOf("1", "2", "3", "4", "5", "6", "7", "8", "9"),
            stringContainer
        )
    }
}

private class StringSplitProcessor(val separator: String) :
    Processor<MutableList<String>> {
    override fun process(element: MutableList<String>): Boolean {
        val resultingStrings = mutableListOf<String>()
        var splitString = false

        element.filter {
            it.isNotBlank()
        }.flatMapTo(resultingStrings) {
            val result = it.split(separator).map { splitStr -> splitStr.trim() }
            if (result.size > 1) splitString = true
            return@flatMapTo result
        }

        println("[${separator.replace("\n", "\\n")}] Split $element into: $resultingStrings")

        element.clear()
        resultingStrings.toCollection(element)

        return splitString
    }
}

