package org.akvo.mockcaddisfly.data;

import java.util.List;

public class Test {

    private String name;
    private String uuid;
    private String ranges;
    private List<Result> results;

    public Test(String name, String uuid, String ranges,
            List<Result> results) {
        this.name = name;
        this.uuid = uuid;
        this.ranges = ranges;
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
