package dev.nies.craft

interface Recipe<T : Any, out R : T> {
    /**
     * Consumes an [element] and returns any resulting elements.
     *
     * Only the resulting values will be included in future crafting passes.
     * Include the original [element] in the results to allow it to be consumed by other recipes.
     */
    fun consume(element: T): List<R>
}