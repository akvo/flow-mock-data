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

package org.akvo.mockcaddisfly

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import org.akvo.mockcaddisfly.data.FileContent
import org.akvo.mockcaddisfly.data.GsonMapper
import org.akvo.mockcaddisfly.data.ResultBuilder
import org.akvo.mockcaddisfly.data.Test
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private val gsonMapper = GsonMapper()
    private val resultBuilder = ResultBuilder()
    private val tests = ArrayList<Test>()

    private val imagePathExtra: String
        get() {
            val file = saveImageToFile()
            return file.absolutePath
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val input = resources.openRawResource(R.raw.tests)
        try {
            val content = FileUtils.readText(input)
            val fileContent = gsonMapper.read(content, FileContent::class.java)
            val fileContentTests = fileContent.tests
            this.tests.addAll(fileContentTests!!)
            Timber.d("Found tests: %d", this.tests.size)
        } catch (e: IOException) {
            Timber.e(e, "error reading tests")
        }
    }

    override fun onResume() {
        super.onResume()
        val testTypeUuid = intent.getStringExtra("caddisflyResourceUuid")
        Timber.d("uid: %s", testTypeUuid)
        val mockTestResult = getMockTestResult(testTypeUuid)
        if (!TextUtils.isEmpty(mockTestResult)) {
            val resultIntent = Intent(intent)
            resultIntent.putExtra("response", mockTestResult)
            val getImageFilePath = imagePathExtra
            resultIntent.putExtra("image", getImageFilePath)
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    private fun saveImageToFile(): File {
        val file = createImageFile()
        val icon = BitmapFactory.decodeResource(resources, R.drawable.caddisfly_img)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            icon.compress(Bitmap.CompressFormat.PNG, 100, out)
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                Timber.e(e)
            }

        }
        return file
    }

    private fun createImageFile(): File {
        val root = Environment.getExternalStorageDirectory()
        val dir = File(
                root.absolutePath + File.separator + "Akvo Caddisfly" + File.separator
                        + "result-images")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return File(dir, "cad_123.png")
    }

    private fun getMockTestResult(testTypeUuid: String?): String {
        if (TextUtils.isEmpty(testTypeUuid)) {
            return ""
        }
        for (test in tests) {
            val testResultsFromFile = test.results
            if (testTypeUuid == test.uuid && testResultsFromFile != null) {
                val testResult = resultBuilder.generateTestResult(test, testResultsFromFile)
                return gsonMapper.write(testResult)
            }
        }
        return ""
    }
}