package dev.nies.craft

class AssemblyLine<T : Any> {
    private val processors = mutableListOf<Processor<T>>()

    /**
     * Installs the given [processor] that will evaluate elements after any prior installed [processors][Processor].
     */
    fun install(processor: Processor<T>) {
        processors += processor
    }

    /**
     * Process the element through each of the [AssemblyLine's][AssemblyLine] processors in order until all processors
     * report they are done. If *any* processor flags an element for further processing, the element will be sent back
     * through the assembly line's processors
     */
    fun process(element: T) {
        while (processElementOnce(element)) {
            // Intentionally do nothing and only use the loop.
            // In the future we want to apply circuit breaker logic here.
        }
    }

    private fun processElementOnce(element: T): Boolean {
        return processors.fold(false) { continueProcessingElement, processor ->
            // We must call the `process` function first since we want its side effects and `||` short-circuits.
            processor.process(element) || continueProcessingElement
        }
    }
}