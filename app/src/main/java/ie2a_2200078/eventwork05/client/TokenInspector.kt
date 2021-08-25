package ie2a_2200078.eventwork05.client

import ie2a_2200078.eventwork05.domain.TokenStorage
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenInspector(
    private val tokenStorage: TokenStorage
) : Authenticator{

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = tokenStorage.get()
        return response.request.newBuilder().addHeader("Authorization", "Bearer $token").build()
    }
}