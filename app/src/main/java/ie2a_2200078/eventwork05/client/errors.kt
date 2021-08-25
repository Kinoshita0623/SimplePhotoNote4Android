package ie2a_2200078.eventwork05.client

import retrofit2.Response
import okhttp3.Response as OkHttpResponse

class UnauthorizedException : IllegalStateException()
class ClientException : Exception()
class NotFoundException : NoSuchElementException()
class InternalServerException : Exception()

fun<T> Response<T>.throwErrors(): Response<T> {
    when(this.code()) {
        400 -> throw ClientException()
        401 -> throw UnauthorizedException()
        404 -> throw NotFoundException()
        500 -> throw InternalServerException()
    }
    return this
}

fun OkHttpResponse.throwErrors(): OkHttpResponse {
    when(this.code) {
        400 -> throw ClientException()
        401 -> throw UnauthorizedException()
        404 -> throw NotFoundException()
        500 -> throw InternalServerException()
    }
    return this
}