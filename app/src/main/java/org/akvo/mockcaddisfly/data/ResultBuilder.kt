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
