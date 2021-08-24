package ie2a_2200078.eventwork05.domain

data class Note(
    val id: Long,
    val title: String,
    val description: String?,
    val files: List<FileProperty>,
    val accountId: Long,
    val account: Account
)