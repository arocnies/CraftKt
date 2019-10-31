package dev.nies.craft

interface Processor<T : Any> {
    /**
     * TODO
     */
    fun process(element: T): Boolean
}