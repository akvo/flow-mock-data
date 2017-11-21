package org.akvo.mockcaddisfly.data;

public class Result {

    private int id;
    private String name;
    private String unit;
    private String value;

    public Result(int id, String name, String unit, String value) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
