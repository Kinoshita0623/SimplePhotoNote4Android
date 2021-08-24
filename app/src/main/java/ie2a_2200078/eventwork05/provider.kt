package ie2a_2200078.eventwork05

import ie2a_2200078.eventwork05.domain.AccountRepository
import ie2a_2200078.eventwork05.domain.TokenStorage

interface AppProvider {
    fun getAccountRepository(): AccountRepository
    fun getTokenStorage(): TokenStorage
}