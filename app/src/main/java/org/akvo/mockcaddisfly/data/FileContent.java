package org.akvo.mockcaddisfly.data;

import java.util.List;

public class FileContent {

    private List<Test> tests;

    public FileContent(List<Test> tests) {
        this.tests = tests;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
