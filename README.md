A library for performing iterative and incremental transformations.



# Example
```kotlin
// Simple
class FactorsCrafter: Crafter<Int> {
    override fun craft(original: Int): List<Int>? {
        if (original.isPrime()) { 
            return null 
        } else {
            original.getFactors()
        }
    }
    // ... //
}
```
```kotlin
fun main() {
    val factorsCraft = Craft<Int>()

    factorsCraft.install(FactorsCrafter())
    
    val factors: List<Int> = factorsCraft.process(listOf(12))
}
```

Factors example: `12` -> `2`, `6` -> `2`, `2`, `3`