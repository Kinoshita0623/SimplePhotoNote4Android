package ie2a_2200078.eventwork05.client

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import ie2a_2200078.eventwork05.domain.TokenStorage
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okio.BufferedSink
import okio.source
import java.net.URL
import java.util.concurrent.TimeUnit

class FileUploader(
    val baseUrl: String,
    val context: Context,
    val tokenStorage: TokenStorage,
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun upload(filename: String, path: String): FileProperty {
        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(114514, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .authenticator(TokenInspector(tokenStorage))
            .build()

        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file",filename, createRequestBody(Uri.parse(path)))



        val requestBody = requestBodyBuilder.build()

        val request = Request.Builder().url(URL("${baseUrl}/api/files")).post(requestBody).build()
        val response = client.newCall(request).execute()
        response.throwErrors()
        return Json.decodeFromString(FileProperty.serializer(), response.body?.string()!!)

    }

    private fun createRequestBody(uri: Uri): RequestBody{
        return object : RequestBody(){
            override fun contentType(): MediaType? {
                val map = MimeTypeMap.getSingleton()
                val mime = map.getExtensionFromMimeType(context.contentResolver.getType(uri))
                return mime?.toMediaType()
            }

            override fun contentLength(): Long {
                return context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.SIZE), null, null, null)?.use{
                    return@use if(it.moveToFirst()){
                        it.getLong(0)
                    }else{
                        null
                    }
                }?: throw IllegalArgumentException("ファイルサイズの取得に失敗しました")
            }

            override fun writeTo(sink: BufferedSink) {
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.source()
                    ?.use{
                        sink.writeAll(it)
                    }

            }
        }
    }
}