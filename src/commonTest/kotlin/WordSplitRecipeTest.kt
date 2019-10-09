package dev.nies.craft

import kotlin.test.Test
import kotlin.test.assertEquals

class WordSplitRecipeTest {
    val testString = """
        1@2@3
        4#5#6
        7&8&9
    """

    @Test fun `Word is split by multiple recipes`() {
        val craft = Craft<String, String>()
        craft.install(StringSplitRecipe("\n"))
        craft.install(StringSplitRecipe("@"))
        craft.install(StringSplitRecipe("#"))
        craft.install(StringSplitRecipe("&"))
        craft.install(KeywordRecipe("3", "Bizz"))
        craft.install(KeywordRecipe("5", "Buzz"))

        assertEquals(
            listOf("1", "2", "Bizz", "4", "Buzz", "6", "7", "8", "9"),
            craft.process(listOf(testString))
        )
    }

    @Test fun `Recipes drop values that are consumed`() {
        val craft = Craft<String, String>()
        craft.install(StringSplitRecipe("\n"))
        craft.install(StringSplitRecipe("@", "2"))
        craft.install(StringSplitRecipe("#"))
        craft.install(StringSplitRecipe("&"))

        assertEquals(
            listOf("1", "3", "4", "5", "6", "7", "8", "9"),
            craft.process(listOf(testString))
        )
    }
}

private class StringSplitRecipe(val separator: String, val dropString: String? = null) : Recipe<String, String> {
    override fun consume(element: String): List<String> =
        element.split(separator)
            .filter { it != dropString && it.isNotBlank() }
            .map { it.trim() }
}

private class KeywordRecipe(val keyword: String, val result: String) : Recipe<String, String> {
    override fun consume(element: String): List<String> =
        if (element == keyword) listOf(result) else listOf(element)
}

