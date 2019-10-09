package dev.nies.craft

class Craft<T : Any, R : T> {
    private val recipes = mutableListOf<Recipe<T, R>>()

    fun install(recipe: Recipe<T, R>) {
        recipes += recipe
    }

    /**
     * Processes [elements] through each installed [Recipe]. Resulting elements are added to the processing queue.
     * Final results are returned when no changes are produced from any recipes.
     */
    fun process(elements: List<T>): List<R> {
        val activeElements = elements.toMutableList()
        val nextElements = mutableListOf<R>()
        val currentSummedResults = mutableListOf<R>()
        val lastSummedResults = mutableListOf<R>()

        while (true) {
            for (recipe in recipes) {
                for (element in activeElements) {
                    nextElements += recipe.consume(element)
                }
                activeElements.clear() // Clear out recipe 1 consumed elements
                activeElements += nextElements // Set recipe 2 elements to consume
                currentSummedResults += nextElements // Collect results from each recipe
                nextElements.clear() // Clear out recipe 3 elements to consume
            }

            @Suppress("UNCHECKED_CAST")
            if (currentSummedResults == lastSummedResults) return activeElements.toList() as List<R>
            lastSummedResults.clear()
            lastSummedResults += currentSummedResults
            currentSummedResults.clear()
        }
    }
}