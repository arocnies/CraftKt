package dev.nies.craft

class Job<T : Any> {
    private val processors = mutableListOf<Processor<T>>()

    /**
     * TODO
     */
    fun install(processor: Processor<T>) {
        processors += processor
    }

    /**
     * TODO
     */
    fun process(element: T) {
        while (processElementOnce(element)) {
            // Do nothing. In the future we want to apply circuit breaker logic here.
        }
    }

    private fun processElementOnce(element: T): Boolean {
        return processors.fold(false) { cont, processor ->
            // We must call the `process` function first since we want its side effects and `||` short circuits.
            processor.process(element) || cont
        }
    }
}