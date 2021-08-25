package ie2a_2200078.eventwork05.domain

import kotlinx.coroutines.flow.Flow

data class Account(
    val id: Long,
    val userName: String,
    val avatarIcon: FileProperty?
)


sealed interface CurrentAccountState {
    object Loading : CurrentAccountState
    data class Fixed(val account: Account) : CurrentAccountState
    data class Error(val error: Throwable) : CurrentAccountState
}
interface AccountRepository {
    suspend fun register(username: String, password: String) : Account

    suspend fun signIn(username: String, password: String): Account

    suspend fun me(): Account

    fun currentAccount(): Flow<CurrentAccountState>

}

interface TokenStorage {
    fun save(token: String)

    fun get(): String
}