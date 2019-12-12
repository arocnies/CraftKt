package dev.nies.craft

interface Processor<T : Any> {
    /**
     * Perform some work with element and return true if the element should be processed further.
     *
     * *In most cases, you want to return true if you mutated the element.*
     * @return **true** to mark the element for further processing
     */
    fun process(element: T): Boolean
}