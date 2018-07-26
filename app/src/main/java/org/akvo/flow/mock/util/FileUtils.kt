
/*
 * Copyright (C) 2018 Stichting Akvo (Akvo Foundation)
 *
 * This file is part of Akvo Flow.
 *
 * Akvo Flow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Akvo Flow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Akvo Flow.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.akvo.flow.mock.util

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
            arrayOutputStream?.let { close(it) }
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
    fun close(closeable: Closeable) {
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
