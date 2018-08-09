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

package org.akvo.flow.mock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import org.akvo.flow.mock.data.FileContent
import org.akvo.flow.mock.util.GsonMapper
import org.akvo.flow.mock.data.ResultBuilder
import org.akvo.flow.mock.data.Test
import org.akvo.flow.mock.util.FileUtils
import org.akvo.flow.mock.util.FileUtils.copyImageResourceToFile
import org.akvo.flow.mock.util.FileUtils.createFile
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*

class CaddisflyMockActivity : AppCompatActivity() {

    private val gsonMapper = GsonMapper()
    private val resultBuilder = ResultBuilder()
    private val tests = ArrayList<Test>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock_caddisfly)
        val input = resources.openRawResource(R.raw.tests)
        try {
            val content = FileUtils.readText(input)
            val fileContent = gsonMapper.read(content, FileContent::class.java)
            this.tests.addAll(fileContent.tests)
            Timber.d("Found tests: ${tests.size}")
        } catch (e: IOException) {
            Timber.e(e, "error reading tests")
        }
    }

    override fun onResume() {
        super.onResume()
        val testTypeUuid: String? = intent.getStringExtra("caddisflyResourceUuid")
        Timber.d("uid: $testTypeUuid")
        val mockTestResult = testTypeUuid?.let { getMockTestResult(it) }
        if (!TextUtils.isEmpty(mockTestResult)) {
            val resultIntent = Intent(intent)
            resultIntent.putExtra("response", mockTestResult)
            resultIntent.putExtra("image", generateImagePath())
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    private fun generateImagePath(): String {
        val file = createCaddisflyImageFile()
        copyImageResourceToFile(file, R.drawable.caddisfly_img, resources)
        return file.absolutePath
    }

    private fun createCaddisflyImageFile(): File {
        return createFile("Akvo Caddisfly${File.separator}result-images", "${UUID.randomUUID()}.png")
    }

    private fun getMockTestResult(testTypeUuid: String): String {
        if (testTypeUuid.isEmpty()) {
            return testTypeUuid
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
