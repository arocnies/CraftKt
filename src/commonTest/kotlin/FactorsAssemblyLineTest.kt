package dev.nies.craft

import kotlin.test.Test
import kotlin.test.assertEquals

class FactorsAssemblyLineTest {
    @Test fun `12 factors into 2, 2, 3`() {
        val factorsAssemblyLine = AssemblyLine<MutableList<Int>>()
        val factors = mutableListOf(12)

        factorsAssemblyLine.install(FactorProcessor(2))
        factorsAssemblyLine.install(FactorProcessor(3))

        factorsAssemblyLine.process(factors)
        assertEquals(listOf(2, 2, 3), factors.sorted())
    }
}

// Example only supports the integer 12
class FactorProcessor(private val factor: Int) : Processor<MutableList<Int>> {
    override fun process(element: MutableList<Int>): Boolean {
        println("[$factor] Processing $element")
        var modifiedContent = false
        val resultingFactors = mutableListOf<Int>()

        element.forEach { num ->
            if (num != factor && num % factor == 0 && num / factor != 0) {
                resultingFactors.add(factor)
                resultingFactors.add(num / factor)
                modifiedContent = true
            } else resultingFactors.add(num)
        }

        element.clear()
        resultingFactors.toCollection(element)

        return modifiedContent
    }
}