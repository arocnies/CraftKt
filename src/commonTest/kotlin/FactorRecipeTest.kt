package dev.nies.craft

import kotlin.test.Test
import kotlin.test.assertEquals

class FactorRecipeTest {
    @Test fun `12 factors into 2, 2, 3`() {
        val factorsCraft = Craft<Int, Int>()

        factorsCraft.install(FactorRecipe())

        val factors: List<Int> = factorsCraft.process(listOf(12))
        assertEquals(listOf(2, 2, 3), factors)
    }
}

// Example only supports the integer 12
class FactorRecipe : Recipe<Int, Int> {
    override fun consume(element: Int): List<Int> {
        return if (element.isPrime()) {
            listOf(element)
        } else {
            element.getFactors()
        }
    }

    fun Int.getFactors(): List<Int> = when (this) {
        12 -> listOf(2, 6)
        6 -> listOf(2, 3)
        2, 3 -> listOf(this)
        else -> error("Test only supports 12")
    }

    fun Int.isPrime() = this == 2 || this == 3
}