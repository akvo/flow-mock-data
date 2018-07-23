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

package org.akvo.mockcaddisfly.data

import org.akvo.mockcaddisfly.data.result.AppFactory
import org.akvo.mockcaddisfly.data.result.DeviceFactory
import org.akvo.mockcaddisfly.data.result.TestResult
import org.akvo.mockcaddisfly.data.result.UserFactory
import java.text.SimpleDateFormat
import java.util.*

class ResultBuilder {

    private val randomTestResultGenerator = RandomTestResultGenerator()

    fun generateTestResult(test: Test, testResultsFromFile: List<Result>): TestResult {
        val app = AppFactory.createDefaultApp()
        val device = DeviceFactory.createDefaultDevice()
        val user = UserFactory.createDefaultUser()
        val results = ArrayList<Result>(testResultsFromFile.size)
        for (result in testResultsFromFile) {
            val resultValue = randomTestResultGenerator.getResultValue(test.ranges).toString() + ""
            results.add(
                    Result(result.id, result.name, result.unit, resultValue))
        }
        val date = SimpleDateFormat(DATE_TIME_FORMAT, Locale.US)
                .format(Calendar.getInstance().time)
        return TestResult(date, app, results, test.name, device, test.uuid,
                DEFAULT_TYPE, user)
    }

    companion object {

        const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
        const val DEFAULT_TYPE = "caddisfly"
    }

}
