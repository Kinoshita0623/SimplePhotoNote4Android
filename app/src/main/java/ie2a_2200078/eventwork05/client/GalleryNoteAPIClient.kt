package ie2a_2200078.eventwork05.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Serializable
data class SignInRequestBody(
    val userName: String,
    val password: String
)

@Serializable
data class RegisterRequestBody(
    val userName: String,
    val password: String
)

@Serializable
data class AuthResponseBody(
    val token: String,
    val account: Account
)

@Serializable
data class CreateNoteBody(
    val title: String,
    val description: String?,
    val fileIds: List<Long>
)

@Serializable
data class FileProperty(
    val id: Long,
    val type: String,
    val name: String,
    @SerialName("originalname") val originalName: String,
    val accountId: Long,
)

@Serializable
data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val accountId: Long,
    val account: Account,
    val isPrivate: Boolean,
    val favoriteCount: Long,
    val files: List<FileProperty>
)
@Serializable
data class Account(
    val id: Long,
    val username: String,
    val avatarIcon: FileProperty?,
    val note: List<Note>?
)

interface GalleryNoteAPIClient {

    @POST("api/accounts/signin")
    suspend fun signIn(@Body body: SignInRequestBody) : Response<AuthResponseBody>

    @POST("api/accounts/register")
    suspend fun register(@Body body: RegisterRequestBody) : Response<AuthResponseBody>

    @POST("api/accounts/me")
    suspend fun me() : Response<Account>

    @POST("api/notes")
    suspend fun createNote(@Body createNote: CreateNoteBody) : Response<Note>

    @GET("api/notes/{id}")
    suspend fun findNote(@Path("id") id: Int) : Response<Note>

    @GET("api/notes")
    suspend fun findTimeline() : Response<List<Note>>

    @GET("api/accounts/my-notes")
    suspend fun findMyNotes() : Response<List<Note>>



}