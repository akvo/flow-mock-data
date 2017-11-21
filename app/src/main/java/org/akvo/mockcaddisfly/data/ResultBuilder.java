package org.akvo.mockcaddisfly.data;

import android.support.annotation.NonNull;

import org.akvo.mockcaddisfly.data.result.App;
import org.akvo.mockcaddisfly.data.result.AppFactory;
import org.akvo.mockcaddisfly.data.result.Device;
import org.akvo.mockcaddisfly.data.result.DeviceFactory;
import org.akvo.mockcaddisfly.data.result.TestResult;
import org.akvo.mockcaddisfly.data.result.User;
import org.akvo.mockcaddisfly.data.result.UserFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ResultBuilder {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String DEFAULT_TYPE = "caddisfly";
    private final RandomTestResultGenerator randomTestResultGenerator = new RandomTestResultGenerator();

    public ResultBuilder() {
    }

    @NonNull
    public TestResult generateTestResult(Test test, List<Result> testResultsFromFile) {
        App app = AppFactory.createDefaultApp();
        Device device = DeviceFactory.createDefaultDevice();
        User user = UserFactory.createDefaultUser();
        List<Result> results = new ArrayList<>(testResultsFromFile.size());
        for (Result result : testResultsFromFile) {
            String resultValue = randomTestResultGenerator.getResultValue(test.getRanges()) + "";
            results.add(
                    new Result(result.getId(), result.getName(), result.getUnit(), resultValue));
        }
        String date = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.US)
                .format(Calendar.getInstance().getTime());
        return new TestResult(date, app, results, test.getName(), device, test.getUuid(),
                DEFAULT_TYPE, user);
    }

}
