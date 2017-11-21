package org.akvo.mockcaddisfly.data.result;

public class User {

    private boolean backDropDetection;
    private String language;

    public User(boolean backDropDetection, String language) {
        this.backDropDetection = backDropDetection;
        this.language = language;
    }

    public boolean isBackDropDetection() {
        return backDropDetection;
    }

    public void setBackDropDetection(boolean backDropDetection) {
        this.backDropDetection = backDropDetection;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
