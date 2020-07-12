package de.stuermerbenjamin.productcatalog.data.remote.api

import java.io.ByteArrayOutputStream
import java.io.InputStream
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ProductMockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val responseString = when {
            uri.endsWith("/products") -> readJsonFromFile("products.json")
            else -> ""
        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_1_1)
            .message(responseString)
            .body(
                responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun readJsonFromFile(filename: String): String {
        val file = "assets/$filename"

        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val inputStream: InputStream = this.javaClass.classLoader.getResourceAsStream(file)

        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        val outputStream = ByteArrayOutputStream()
        outputStream.write(bytes)
        outputStream.close()
        inputStream.close()

        return outputStream.toString()
    }
}
