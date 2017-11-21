package org.akvo.mockcaddisfly.data.result;

public class App {

    private String appVersion;
    private String language;

    public App(String appVersion, String language) {
        this.appVersion = appVersion;
        this.language = language;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
