package org.akvo.mockcaddisfly

import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

import timber.log.Timber

object FileUtils {

    private const val BUFFER_SIZE = 2048

    /**
     * reads data from an InputStream into a string.
     */
    @Throws(IOException::class)
    fun readText(inputStream: InputStream): String {
        var arrayOutputStream: ByteArrayOutputStream? = null
        try {
            arrayOutputStream = read(inputStream)
            return arrayOutputStream.toString()
        } finally {
            close(inputStream)
            arrayOutputStream?.flush()
            close(arrayOutputStream)
        }
    }

    /**
     * reads the contents of an InputStream into a ByteArrayOutputStream.
     */
    @Throws(IOException::class)
    private fun read(inputStream: InputStream): ByteArrayOutputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        copy(inputStream, byteArrayOutputStream)
        return byteArrayOutputStream
    }

    /**
     * Helper function to close a Closeable instance
     */
    private fun close(closeable: Closeable?) {
        if (closeable == null) {
            return
        }
        try {
            closeable.close()
        } catch (e: IOException) {
            Timber.e(e.message)
        }

    }

    @Throws(IOException::class)
    private fun copy(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(BUFFER_SIZE)
        var length = input.read(buffer)

        while(length != -1) {
            output.write(buffer, 0, length)
            length = input.read(buffer)
        }
    }
}
