package ie2a_2200078.eventwork05

sealed interface State<T> {
    data class Fixed<T>(val content: Content<T>) : State<T>
    data class Loading<T>(val content: Content<T>) : State<T>
    data class Error<T>(val content: Content<T>, val error: Throwable) : State<T>
}

sealed interface Content<T> {
    data class Exit<T>(val content: T) : Content<T>
    class NotExit<T>: Content<T>
}