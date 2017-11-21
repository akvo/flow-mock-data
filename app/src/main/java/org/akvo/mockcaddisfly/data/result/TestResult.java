package org.akvo.mockcaddisfly.data.result;

import org.akvo.mockcaddisfly.data.Result;

import java.util.List;

public class TestResult {

    private String testDate;
    private App app;
    private List<Result> result;
    private String name;
    private Device device;
    private String uuid;
    private String type;
    private User user;

    public TestResult(String testDate, App app, List<Result> result, String name, Device device,
            String uuid, String type, User user) {
        this.testDate = testDate;
        this.app = app;
        this.result = result;
        this.name = name;
        this.device = device;
        this.uuid = uuid;
        this.type = type;
        this.user = user;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
