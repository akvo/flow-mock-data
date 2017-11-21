package org.akvo.mockcaddisfly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import org.akvo.mockcaddisfly.data.FileContent;
import org.akvo.mockcaddisfly.data.GsonMapper;
import org.akvo.mockcaddisfly.data.Result;
import org.akvo.mockcaddisfly.data.ResultBuilder;
import org.akvo.mockcaddisfly.data.Test;
import org.akvo.mockcaddisfly.data.result.TestResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private final GsonMapper gsonMapper = new GsonMapper();
    private final ResultBuilder resultBuilder = new ResultBuilder();
    private final List<Test> tests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputStream input = getResources().openRawResource(R.raw.tests);
        String content = null;
        try {
            content = FileUtils.readText(input);
        } catch (IOException e) {
            Timber.e(e, "error reading tests");
        }
        FileContent fileContent = gsonMapper.read(content, FileContent.class);
        List<Test> fileContentTests = fileContent.getTests();
        this.tests.addAll(fileContentTests);
        Timber.d("Found tests: %d", this.tests.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String testTypeUuid = getIntent().getStringExtra("caddisflyResourceUuid");
        Timber.d("uid: %s", testTypeUuid);
        String mockTestResult = getMockTestResult(testTypeUuid);
        if (!TextUtils.isEmpty(mockTestResult)) {
            Intent resultIntent = new Intent(getIntent());
            resultIntent.putExtra("response", mockTestResult);
            setResult(Activity.RESULT_OK, resultIntent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    public String getMockTestResult(@Nullable String testTypeUuid) {
        if (TextUtils.isEmpty(testTypeUuid)) {
            return "";
        }
        for (Test test : tests) {
            List<Result> testResultsFromFile = test.getResults();
            if (testTypeUuid.equals(test.getUuid()) && testResultsFromFile != null) {
                TestResult testResult = resultBuilder.generateTestResult(test, testResultsFromFile);
                return gsonMapper.write(testResult);
            }
        }
        return "";
    }
}
