
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

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.annotation.Nullable
import org.akvo.flow.mock.R
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.Adler32
import java.util.zip.CheckedOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

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
    private fun close(closeable: Closeable) {
        try {
            closeable.close()
        } catch (e: IOException) {
            Timber.e(e)
        }

    }

    @Throws(IOException::class)
    fun copy(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(BUFFER_SIZE)
        var length = input.read(buffer)

        while(length != -1) {
            output.write(buffer, 0, length)
            length = input.read(buffer)
        }
    }

    @Nullable
    @Throws(IOException::class)
    fun copyInputStreamToFile(inputStream: InputStream, destinationFile: File)    {
        var outputStream: OutputStream? = null
        try {
            outputStream = FileOutputStream(destinationFile)
            copy(inputStream, outputStream)
            outputStream.flush()
        } catch (e: FileNotFoundException) {
            Timber.e(e)
        } finally {
            close(inputStream)
            outputStream?.let { close(it) }
        }
    }

    @Nullable
    @Throws(IOException::class)
    fun copyFile(originalFile: File, destinationFile: File)    {
        var outputStream: OutputStream? = null
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(originalFile)
            outputStream = FileOutputStream(destinationFile)
            copy(inputStream, outputStream)
            outputStream.flush()
        } catch (e: FileNotFoundException) {
            Timber.e(e)
        } finally {
            inputStream?.let { close(it) }
            outputStream?.let { close(it) }
        }
    }

    fun copyImageResourceToFile(file: File, imageResource: Int, resources: Resources) {
        val icon = BitmapFactory.decodeResource(resources, imageResource)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            icon.compress(Bitmap.CompressFormat.PNG, 100, out)
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            out?.let { close(it) };
        }
    }

    fun copyImageResourceToFile(imagePath: Uri, resources: Resources) {
        copyImageResourceToFile(File(imagePath.path!!), R.drawable.akvo_image, resources)
    }

    fun createFile(folderName: String, fileName: String): File {
        val dir = createFolder(folderName)
        return File(dir, fileName)
    }


    fun readZipEntry(file: File?): String {
        val zipFile = ZipFile(file)
        val entry = zipFile.entries().nextElement()
        val bufferedReader = zipFile.getInputStream(entry).bufferedReader()
        val json = bufferedReader.use { it.readText() }
        bufferedReader.close()
        zipFile.close()
        return json
    }

    fun writeToZipFile(
        destinationDataFolder: File?,
        dataString: String,
        fileName: String
    ) {
        val newFile = File(destinationDataFolder, fileName)
        writeZipFile(destinationDataFolder!!, newFile.name, dataString)
    }

    fun obtainFolder(folderName: String): File {
        val root = Environment.getExternalStorageDirectory()
        val dir = File("${root.absolutePath + File.separator}$folderName")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    @Throws(IOException::class)
    private fun writeZipFile(zipFolder: File, zipFileName: String, formInstanceData: String) {
        val zipFile = File(zipFolder, zipFileName)
        Timber.d("Writing zip to file %s", zipFile.name)
        val fout = FileOutputStream(zipFile)
        val checkedOutStream = CheckedOutputStream(fout, Adler32())
        val zos = ZipOutputStream(checkedOutStream)
        zos.putNextEntry(ZipEntry("data.json"))
        val allBytes: ByteArray = formInstanceData.toByteArray()
        zos.write(allBytes, 0, allBytes.size)
        zos.closeEntry()
        zos.close()
        fout.close()
    }

    private fun createFolder(folderName: String): File {
        val root = Environment.getExternalStorageDirectory()
        val dir = File("${root.absolutePath + File.separator}$folderName")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }
}
